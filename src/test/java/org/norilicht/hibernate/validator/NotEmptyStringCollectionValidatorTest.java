package org.norilicht.hibernate.validator;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ValidationException;

import org.junit.Test;
import org.norilicht.hibernate.validator.annotation.NotEmtpyStringCollection;
import org.norilicht.hibernate.validator.util.HibernateValidatorUtils;

public class NotEmptyStringCollectionValidatorTest {
	@Test
	public void collectionIsNull() throws Exception {
		NotEmptyStringCollectionObject obj = new NotEmptyStringCollectionObject();
		List<String> collection = null;
		obj.setCollection(collection);

		HibernateValidatorUtils.validate(obj);
	}

	@Test
	public void collectionIsEmpty() throws Exception {
		NotEmptyStringCollectionObject obj = new NotEmptyStringCollectionObject();
		List<String> collection = new ArrayList<String>();
		obj.setCollection(collection);

		HibernateValidatorUtils.validate(obj);
	}

	@Test(expected = ValidationException.class)
	public void stringInCollectionIsNull() throws Exception {
		NotEmptyStringCollectionObject obj = new NotEmptyStringCollectionObject();
		List<String> collection = new ArrayList<String>();
		collection.add(null);
		obj.setCollection(collection);

		HibernateValidatorUtils.validate(obj);
	}

	@Test(expected = ValidationException.class)
	public void stringInCollectionIsEmpty() throws Exception {
		NotEmptyStringCollectionObject obj = new NotEmptyStringCollectionObject();
		List<String> collection = new ArrayList<String>();
		collection.add("");
		obj.setCollection(collection);

		HibernateValidatorUtils.validate(obj);
	}

	@Test
	public void validationMessage() {
		NotEmptyStringCollectionObject obj = new NotEmptyStringCollectionObject();
		List<String> collection = null;
		obj.setCollection(collection);

		try {
			HibernateValidatorUtils.validate(obj);
		} catch (ValidationException e) {
			assertEquals("may not be empty", e.getMessage());
		}
	}

	public static class NotEmptyStringCollectionObject {
		@NotEmtpyStringCollection
		private List<String> collection;

		public List<String> getCollection() {
			return collection;
		}

		public void setCollection(List<String> collection) {
			this.collection = collection;
		}

	}
}
