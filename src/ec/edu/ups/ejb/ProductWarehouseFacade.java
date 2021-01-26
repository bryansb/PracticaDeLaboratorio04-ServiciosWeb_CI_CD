package ec.edu.ups.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ec.edu.ups.entities.ProductWarehouse;

@Stateless
public class ProductWarehouseFacade extends AbstractFacade<ProductWarehouse> {

	private static final String FIND_BY_ID_QRY = "SELECT SUM(pw.stock) "
			+ "FROM ProductWarehouse pw WHERE pw.product.id = :id "
			+ "AND pw.deleted = 0";

	@PersistenceContext(unitName = "Practica03")
	private EntityManager em;

	public ProductWarehouseFacade() {
		super(ProductWarehouse.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public List<ProductWarehouse> findAllOrderedByProductName(String productName) {
		String[][] attributes = {{"product", "name"}, { "deleted" } };
		String[] values = {"like&%" + productName + "%","equal&0" };
		String[] order = { "product", "name" };
		return super.findByPath(attributes, values, order, 0, 0, true, false);
	}

	public List<ProductWarehouse> findByWarehouseId(int warehouseId, String productName) {
		String[][] attributes = { { "warehouse", "id" }, {"product", "name"}, { "deleted" } };
		String[] values = { "equal&" + warehouseId, "like&%" + productName + "%","equal&0" };
		String[] order = { "product", "name" };
		return super.findByPath(attributes, values, order, 0, 0, true, false);
	}

	public List<ProductWarehouse> findByWarehouseIdAll(int warehouseId) {
		String[][] attributes = { { "warehouse", "id" } };
		String[] values = { "equal&" + warehouseId };
		String[] order = { "product", "name" };
		return super.findByPath(attributes, values, order, 0, 0, true, false);
	}

	public List<ProductWarehouse> findByCategoryId(int categoryId, String productName) {
		String[][] attributes = { { "product", "category", "id" }, {"product", "name"},
				{ "deleted" } };
		String[] values = { "equal&" + categoryId, "like&%" + productName + "%", "equal&0" };
		String[] order = { "product", "name" };
		return super.findByPath(attributes, values, order, 0, 0, true, false);
	}

	public List<ProductWarehouse> findByWarehouseAndCategoryId(int warehouseId,
			int categoryId, String productName) {
		String[][] attributes = { {"product", "name"}, { "product", "category", "id" },
				{ "warehouse", "id" }, { "deleted" } };
		String[] values = { "like&%" + productName + "%", "equal&" + categoryId, "equal&" + warehouseId,
				"equal&0" };
		String[] order = { "product", "name" };
		return super.findByPath(attributes, values, order, 0, 0, true, false);
	}

	public List<ProductWarehouse> findProductWarehouseByProductName(
			String name) {
		String[][] attributes = { { "product", "name" }, { "deleted" } };
		String[] values = { "equal&" + name, "equal&0" };
		String[] order = { "product", "name" };
		return super.findByPath(attributes, values, order, 0, 0, true, false);
	}

	public boolean isPersisted(String name, int id) {

		List<ProductWarehouse> productsWarehouse;
		String[][] attributes = { { "product", "name" },
				{ "warehouse", "id" } };
		String[] values = { "equal&" + name, "equal&" + id };
		productsWarehouse = super.findByPath(attributes, values, null, 0, 0,
				true, false);

		if (productsWarehouse == null || productsWarehouse.isEmpty()) {
			return false;
		}

		return true;
	}

	public int getStockByProduct(int id) {
		int sum;
		try {
			sum = ((Long) getEntityManager().createQuery(FIND_BY_ID_QRY)
					.setParameter("id", id).getSingleResult()).intValue();
		} catch (Exception e) {
			return 0;
		}
		return sum;
	}

}
