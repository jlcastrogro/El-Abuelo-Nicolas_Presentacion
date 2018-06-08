package elabuelonicolas.managedBean.producto;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import elabuelonicolas.bd.domain.Producto;
import elabuelonicolas.service.producto.ProductoService;

@Named
public class ProductoBean {
	@Inject
	ProductoService productoService;
	private List<Producto> productoList;

	public List<Producto> getProductoList() {
		if (productoList == null)
			setProductoList(productoService.findAll());

		return productoList;
	}

	private void setProductoList(List<Producto> productoList) {
		this.productoList = productoList;
	}

	public void onRowEdit(RowEditEvent event) {
		Producto producto = ((Producto) event.getObject());
		System.out.println("Datos producto: " + producto.getId());
		productoService.update(producto);

		FacesMessage msg = new FacesMessage("Producto editado", producto.getId()
				.toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edición cancelada",
				((Producto) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		System.out.println("Verifica: " + newValue);

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Producto modificado", "Antes: " + oldValue + ", Ahora: "+ newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	public String deleteAction(Producto order){   
		productoList.remove(order);
		productoService.delete(order.getId());
		return null;
	}
}