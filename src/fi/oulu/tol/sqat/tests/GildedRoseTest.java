package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

	// Example scenarios for testing
	// Item("+5 Dexterity Vest", 10, 20));
	// Item("Aged Brie", 2, 0));
	// Item("Elixir of the Mongoose", 5, 7));
	// Item("Sulfuras, Hand of Ragnaros", 0, 80));
	// Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
	// Item("Conjured Mana Cake", 3, 6));

	@Test
	public void testUpdateEndOfDay_AgedBrie_Quality_10_11() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 2, 10));

		// Act
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		assertEquals(11, itemBrie.getQuality());
	}

	@Test
	public void testUpdateEndOfDay_Sulfuras_Quality_Unchanged() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Sulfuras", 2, 80));

		// Act
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item itemSulfuras = items.get(0);
		assertEquals(80, itemSulfuras.getQuality());
	}

	@Test
	public void testUpdateEndOfDay_QualityAndSellInDecrement() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Test Item", 24, 16));

		// Act
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item itemTestItem = items.get(0);
		assertEquals(15, itemTestItem.getQuality());
		assertEquals(23, itemTestItem.getSellIn());
	}

	@Test
	public void testUpdateEndOfDay_QualityNeverNegative() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Old Apples", 2, 0));

		// Act
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item itemOldApples = items.get(0);
		assertEquals(0, itemOldApples.getQuality());
	}

	@Test
	public void testUpdateEndOfDay_SellInPassedDegradesQualityTwice() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Rare Parrot", -3, 10));

		// Act
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item itemRareParrot = items.get(0);
		assertEquals(8, itemRareParrot.getQuality());
	}

	@Test
	public void testUpdateEndOfDay_QualityNeverHigherThan50() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Aged Brie", 2, 50));

		// Act
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item itemBrie = items.get(0);
		assertEquals(50, itemBrie.getQuality());
	}

	@Test
	public void testUpdateEndOfDay_BackstagePasses_QualityIncreases() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes", 15, 10));

		// Act
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item itemBackstagePasses = items.get(0);
		assertEquals(11, itemBackstagePasses.getQuality());
	}

	@Test
	public void testUpdateEndOfDay_BackstagePasses_QualityIncreasesDoubleWhen10DaysOrLess() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes", 10, 10));

		// Act
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item itemBackstagePasses = items.get(0);
		assertEquals(12, itemBackstagePasses.getQuality());
	}

	@Test
	public void testUpdateEndOfDay_BackstagePasses_QualityIncreasesTripleWhen5DaysOrLess() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes", 5, 10));

		// Act
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item itemBackstagePasses = items.get(0);
		assertEquals(13, itemBackstagePasses.getQuality());
	}

	@Test
	public void testUpdateEndOfDay_BackstagePasses_QualityBecomes0AfterConcert() {
		// Arrange
		GildedRose store = new GildedRose();
		store.addItem(new Item("Backstage passes", 0, 35));

		// Act
		store.updateEndOfDay();

		// Assert
		List<Item> items = store.getItems();
		Item itemBackstagePasses = items.get(0);
		assertEquals(0, itemBackstagePasses.getQuality());
	}
}
