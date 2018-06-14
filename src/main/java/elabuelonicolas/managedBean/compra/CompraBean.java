package elabuelonicolas.managedBean.compra;

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

import elabuelonicolas.bd.domain.Compra;
import elabuelonicolas.service.compra.CompraService;

@Named
@ViewScoped
public class CompraBean {
	@Inject
	CompraService compraService;
	private List<Compra> compraList;
	private List<Compra> filteredComp;
	
	@PostConstruct		
	public List<Compra> getCompraList() {
		if (compraList == null)
			setCompraList(compraService.findAll());

		return compraList;
	}

	private void setCompraList(List<Compra> compraList) {
		this.compraList = compraList;
	}

	public void onRowEdit(RowEditEvent event) {
		Compra compras = ((Compra) event.getObject());
		System.out.println("Datos de Compra: " + compras.getId());
		compraService.update(compras);

		FacesMessage msg = new FacesMessage("Compra editada", compras.getId()
				.toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edición cancelada",
				((Compra) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		System.out.println("Verifica: " + newValue);

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Compra modificada", "Antes: " + oldValue + ", Ahora: "+ newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public String deleteAction(Compra order){   
		compraList.remove(order);
		compraService.delete(order.getId());
		return null;
	}
	
	public void onRowSelect(SelectEvent event) {
		Compra compra = ((Compra) event.getObject());
		System.out.println("Datos compraSelect: " + compra.getId());
		compraService.update(compra); 
		
		FacesMessage msg = new FacesMessage("Compra seleccionado", compra.getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void onRowUnselect(UnselectEvent event) {
		Compra compra = ((Compra) event.getObject());
		System.out.println("Datos compraUnselect: " + compra.getId());
		compraService.update(compra); 
		
		FacesMessage msg = new FacesMessage("Compra deseleccionado", compra.getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public List<Compra> getFilteredCompra(){
		return filteredComp;
	}
	
	public void setFilteredCompra(List<Compra> filteredComp) {
		this.filteredComp = filteredComp;
	}
}
