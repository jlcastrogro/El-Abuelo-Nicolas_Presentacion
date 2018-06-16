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
	//Campos cliente
	private String nombre;
    private String contacto;
    private String telefono;
    private String email;
    private String rfc;
    //Campos direccion
    private String pais;
    private String estado;
    private String ciudad;
    private String colonia;
    private String calle;
    private String numero;
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
	    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
    public String getContacto() {
        return contacto;
    }
    
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getRfc() {
        return rfc;
    }
    
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }
    
    public String getPais() {
        return pais;
    }
    
    public void setPais(String pais) {
        this.pais = pais;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
 
    public String getCiudad() {
        return ciudad;
    }
    
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    
    public String getColonia() {
        return colonia;
    }
    
    public void setColonia(String colonia) {
        this.colonia = colonia;
    }
    
    public String getCalle() {
        return calle;
    }
    
    public void setCalle(String calle) {
        this.calle = calle;
    }
    
    public String getNumero() {
        return numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    
    public void save() {
        
    	//Insertar dirección
    	
    	//Insertar cliente
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre(nombre);
        nuevoCliente.setContacto(contacto);
        nuevoCliente.setTelefono(telefono);
        nuevoCliente.setEmail(email);
        nuevoCliente.setRfc(rfc);
        
        clienteService.create(nuevoCliente);
        nuevoCliente.setId(clienteService.last().getId()); 
        clienteList.add(nuevoCliente);
       
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Cliente " + nombre + " registrado "));
        
        resetFormulario();
       
    }
    
    @SuppressWarnings("null")
	public void resetFormulario() {
    	this.nombre = null;
        this.contacto = null;
        this.telefono = null;
        this.email = null;
        this.rfc = null;
        this.pais = null;
        this.estado = null;
        this.ciudad = null;
        this.colonia = null;
        this.calle = null;
        this.numero = null;
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
