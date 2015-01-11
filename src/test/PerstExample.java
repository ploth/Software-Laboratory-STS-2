package test;

import java.io.IOException;
import java.util.Iterator;

import org.garret.perst.Database;
import org.garret.perst.IPersistentSet;
import org.garret.perst.IterableIterator;
import org.garret.perst.Link;
import org.garret.perst.Persistent;
import org.garret.perst.Storage;
import org.garret.perst.StorageFactory;

public class PerstExample {
	class Subclass extends Persistent {
		String name;

		public Subclass(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	class Category extends Persistent {

		String name;
		Category superCategory;
		Link<Category> subCategories;

		public Category(String name) {
			this.name = name;
			subCategories = PerstExample.INSTANCE().getStorage().createLink();
			PerstExample.INSTANCE().getDB().addRecord(this);
			// if consistency checks are done by the caller, addRecord should be
			// called after the check in the caller code
		}

		public Category(String name, Storage storage) {
			this.name = name;
			subCategories = storage.createLink();
		}

		public Iterator<Category> getSubCategories() {
			return this.subCategories.iterator();
		}

		public void addSubCategory(Category Category) {
			this.subCategories.add(Category);
		}

		public String getName() {
			return name;
		}

		public void setName(String value) {
			this.name = value;
		}

		public Category getSuperCategory() {
			return superCategory;
		}

		public void setSuperCategory(Category Category) {
			this.superCategory = Category;

		}

	}

	private Storage storage;
	private Database db;
	private static PerstExample instance;
	private static String defaultDatabase = "src/test/sample.dbs";

	// should also have a default path, otherwise will be created in the eclipse
	// project root

	public static PerstExample INSTANCE() {
		if (instance == null)
			instance = new PerstExample(defaultDatabase);
		return instance;
	}

	private PerstExample(String dbName) {
		storage = StorageFactory.getInstance().createStorage();
		storage.open(dbName, 1024);
		db = new Database(storage, false);
		instance = this;
	}

	protected Database getDB() {
		return db;
	}

	protected Storage getStorage() {
		return storage;
	}

	protected void closeDB() {
		db.dropTable(Category.class);
		db.commitTransaction();
		storage.close();
	}

	private Category createCategory(String name, Category superCategory) {
		Category category = new Category(name);
		if (superCategory != null) {
			category.setSuperCategory(superCategory);
			superCategory.addSubCategory(category);
		}
		return category;
	}

	private void createSampleCategory() {
		// Create a root Category Rock with some sub-Category.
		// Should check whether that name already exists.
		Category spielzeug = createCategory("Spielzeug", null);
		Category werkzeuge = createCategory("Werkzeuge", null);

		createCategory("Tier-Spielzeug", spielzeug);
		createCategory("Burgen", spielzeug);
		createCategory("Ritter-Spielzeug", spielzeug);
		createCategory("Holz-Spielzeug", spielzeug);

		createCategory("Tischler-Werkzeug", werkzeuge);
		createCategory("Schleifmaschinen", werkzeuge);

	}

	private void retrieveData() {
	// navigational access after selection. Printout all  subCategories (level 1) of Rock
		System.out.println("Get all sub Category of 'Spielzeug' by programmatic navigation.");
		for (Category g : db.<Category> select(Category.class, "name ='Spielzeug'")) {
			Iterator<Category> gIterator = g.getSubCategories();
			while (gIterator.hasNext()) {
				Category gs = (Category) gIterator.next();
				System.out.println("SubCategory is: " + gs.getName());
			}

		}
		System.out.println("\n");

		System.out.println("Get all super Category of sub Categories that contain 'Spielzeug' in their name.");
	// Query Link data and return parent. Get all subCategories where the name is like *zeug* and return the parent
		for (Category g : db.<Category> select(Category.class,
				"contains subCategories with name like '%zeug%'")) {
			System.out.println("SuperCategory is: " + g.getName());

		}
		System.out.println("\n");

		System.out.println("Get all sub Category of super Categories that contain 'Spielzeug' in their name.");
		// Query navigational path return child. Get all subCategories that have a parent   where the name is like *Spielzeug* and return the parent subCategory.
		for (Category g : db.<Category> select(Category.class,
				"superCategory != null and superCategory.name like '%Spielzeug%'")) {
			System.out.println("SubCategory is:  " + g.getName());

		}

	}

	static public void main(String[] args) {
		// get default instance of the storage
		PerstExample m = PerstExample.INSTANCE();
		m.createSampleCategory();
		m.retrieveData();
		m.closeDB();
	}

}
