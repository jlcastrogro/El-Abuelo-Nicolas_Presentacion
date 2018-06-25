package elabuelonicolas.managedBean.listaventa;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import elabuelonicolas.bd.domain.Listaventa;
import elabuelonicolas.service.listaventa.ListaventaService;

@Named
@ViewScoped
public class ListaventaBean {
	@Inject
	ListaventaService listaventasService;
	List<Listaventa> listaventasList;
	private List<Listaventa> filteredLisven;
	
	@PostConstruct
	public List<Listaventa> getListaventaList() {
		if (listaventasList == null)
			setListaventaList(listaventasService.findAll());

		return listaventasList;
	}

	private void setListaventaList(List<Listaventa> listaventasList) {
		this.listaventasList = listaventasList;
	}

	public void onRowEdit(RowEditEvent event) {
		Listaventa listaventas = ((Listaventa) event.getObject());
		System.out.println("Datos de Listaventa: " + listaventas.getId());
		listaventasService.update(listaventas);

		FacesMessage msg = new FacesMessage("Listaventa editada", listaventas.getId()
				.toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edición cancelada",
				((Listaventa) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		System.out.println("Verifica: " + newValue);

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Listaventa modificada", "Antes: " + oldValue + ", Ahora: "+ newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	public String deleteAction(Listaventa order){   
		listaventasList.remove(order);
		listaventasService.delete(order.getId());
		return null;
	}
	
	public void onRowSelect(SelectEvent event) {
		Listaventa listaventa = ((Listaventa) event.getObject());
		System.out.println("Datos proveedorSelect: " + listaventa.getId());
		listaventasService.update(listaventa); 
		
		FacesMessage msg = new FacesMessage("Listaventa seleccionado", listaventa.getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void onRowUnselect(UnselectEvent event) {
		Listaventa listaventa = ((Listaventa) event.getObject());
		System.out.println("Datos proveedorUnselect: " + listaventa.getId());
		listaventasService.update(listaventa); 
		
		FacesMessage msg = new FacesMessage("Listaventa deseleccionado", listaventa.getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public List<Listaventa> getFilteredListaventa(){
		return filteredLisven;
	}
	
	public void setFilteredListaventa(List<Listaventa> filteredListaven) {
		this.filteredLisven = filteredListaven;
	}

	public List<Listaventa> getBest() {
		if (listaventasList == null) 
			setListaventaList(listaventasService.best());

		return listaventasList;
	}
}
