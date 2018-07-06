package com.bookstore.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bookstore.model.Book;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestControllerMethods extends BookStoreTest {

	private Book book = null;

	@Autowired
	private ObjectMapper mapper;

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

	}

	/*
	 * To test the addBook method here we created the book object and adding the
	 * object and matching the status with httpstatus.created
	 */
	@Test
	public void testaddBook() throws Exception {
		book = new Book();
		book.setBookISBN_ID("123");
		book.setBookName("Java");
		book.setBookCategory("Technology");
		book.setDescription("Java Fundamental");
		String json = mapper.writeValueAsString(book);
		mockMvc.perform(post("/books/add").contentType(MediaType.APPLICATION_JSON).content(json)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

	/*
	 * To test the negative scenario where we are adding existing book the book is
	 * already available so conflicting the status so returning httpstatus.conflicts
	 */
	@Test
	public void testaddExistingBook() throws Exception {
		book = new Book();
		book.setBookISBN_ID("9780061122415");
		book.setBookName("Java");
		book.setBookCategory("Technology");
		book.setDescription("Java Fundamental");
		String json = mapper.writeValueAsString(book);
		mockMvc.perform(post("/books/add").contentType(MediaType.APPLICATION_JSON).content(json)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isConflict());
	}

	/*
	 * test for returning the book for given ISBN id
	 */
	@Test
	public void testGetBook() throws Exception {
		mockMvc.perform(get("/books/9780061122415")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.bookCategory").value("Fiction"))
				.andExpect(jsonPath("$.bookISBN_ID").value("9780061122415"))
				.andExpect(jsonPath("$.bookName").value("The Alchemist")).andExpect(jsonPath("$.description").value(
						"The story of the treasures Santiago finds along the way teaches us, as only a few stories can, about the essential wisdom of listening to our hearts, learning to read the omens strewn along life's path, and, above all, following our dreams."));

	}

}