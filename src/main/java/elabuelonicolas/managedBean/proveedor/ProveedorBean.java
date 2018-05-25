package elabuelonicolas.managedBean.proveedor;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import elabuelonicolas.bd.domain.Proveedor;
import elabuelonicolas.service.proveedor.ProveedorService;

@Named
public class ProveedorBean {
	@Inject
	ProveedorService proveedorService;
	private List<Proveedor> proveedorList;

	public List<Proveedor> getProveedorList() {
		if (proveedorList == null)
			setProveedorList(proveedorService.findAll());

		return proveedorList;
	}

	private void setProveedorList(List<Proveedor> proveedorList) {
		this.proveedorList = proveedorList;
	}

	public void onRowEdit(RowEditEvent event) {
		Proveedor proveedor = ((Proveedor) event.getObject());
		System.out.println("Datos proveedor: " + proveedor.getId());
		proveedorService.update(proveedor);

		FacesMessage msg = new FacesMessage("Proveedor editado", proveedor.getId()
				.toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edición cancelada",
				((Proveedor) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		System.out.println("Verifica: " + newValue);

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Proveedor modificado", "Antes: " + oldValue + ", Ahora: "+ newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
}
