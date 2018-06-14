package elabuelonicolas.managedBean.cliente;

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

import elabuelonicolas.bd.domain.Cliente;
import elabuelonicolas.service.cliente.ClienteService;

@Named
@ViewScoped
public class ClienteBean {
	@Inject
	ClienteService clienteService;
	private List<Cliente> clienteList;
	private List<Cliente> filteredCli;

	@PostConstruct
	public List<Cliente> getClienteList() {
		if (clienteList == null)
			setClienteList(clienteService.findAll());

		return clienteList;
	}

	private void setClienteList(List<Cliente> clienteList) {
		this.clienteList = clienteList;
	}

	public void onRowEdit(RowEditEvent event) {
		Cliente cliente = ((Cliente) event.getObject());
		System.out.println("Datos cliente: " + cliente.getId());
		clienteService.update(cliente);

		FacesMessage msg = new FacesMessage("Cliente editado", cliente.getId()
				.toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edición cancelada",
				((Cliente) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		System.out.println("Verifica: " + newValue);

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente modificado", "Antes: " + oldValue + ", Ahora: "+ newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public String deleteAction(Cliente order){   
		clienteList.remove(order);
		clienteService.delete(order.getId());
		return null;
	}
	
	public void onRowSelect(SelectEvent event) {
		Cliente cliente = ((Cliente) event.getObject());
		System.out.println("Datos clienteSelect: " + cliente.getId());
		clienteService.update(cliente); 
		
		FacesMessage msg = new FacesMessage("Cliente seleccionado", cliente.getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void onRowUnselect(UnselectEvent event) {
		Cliente cliente = ((Cliente) event.getObject());
		System.out.println("Datos clienteUnselect: " + cliente.getId());
		clienteService.update(cliente); 
		
		FacesMessage msg = new FacesMessage("Cliente deseleccionado", cliente.getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public List<Cliente> getFilteredCliente(){
		return filteredCli;
	}
	
	public void setFilteredCliente(List<Cliente> filteredCli) {
		this.filteredCli = filteredCli;
	}
}
