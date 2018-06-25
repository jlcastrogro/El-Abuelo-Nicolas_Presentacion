package elabuelonicolas.managedBean.cliente;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
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
	//Campos cliente
	private String nombre;
    private String contacto;
    private String telefono;
    private String email;
    private String rfc;
    private String estado;
    private String municipio;
    private String localidad;
    private String codigopostal;
    private String asentamiento;
    private String calle;
    private String numero;
    
    private ArrayList<String> nombres;
    private ArrayList<Integer> ids;

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
	
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente editado", cliente.getNombre()
				.toString());
		RequestContext.getCurrentInstance().showMessageInDialog(msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Edición cancelada",
				((Cliente) event.getObject()).getId().toString());
		RequestContext.getCurrentInstance().showMessageInDialog(msg);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		System.out.println("Verifica: " + newValue);

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente modificado", "Antes: " + oldValue + ", Ahora: "+ newValue);
			RequestContext.getCurrentInstance().showMessageInDialog(msg);
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
        
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getMunicipio() {
        return municipio;
    }
    
    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }
 
    public String getLocalidad() {
        return localidad;
    }
    
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }
    
    public String getCodigopostal() {
        return codigopostal;
    }
    
    public void setCodigopostal(String codigopostal) {
        this.codigopostal = codigopostal;
    }
    
    public String getAsentamiento() {
        return asentamiento;
    }
    
    public void setAsentamiento(String asentamiento) {
        this.asentamiento = asentamiento;
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
        
    	//Insertar cliente
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre(nombre);
        nuevoCliente.setContacto(contacto);
        nuevoCliente.setTelefono(telefono);
        nuevoCliente.setEmail(email);
        nuevoCliente.setRfc(rfc);
        nuevoCliente.setEstado(estado);
        nuevoCliente.setMunicipio(municipio);
        nuevoCliente.setLocalidad(localidad);
        nuevoCliente.setCodigopostal(Integer.parseInt(codigopostal));
        nuevoCliente.setAsentamiento(asentamiento);
        nuevoCliente.setCalle(calle);
        nuevoCliente.setNumero(Integer.parseInt(numero));
        nuevoCliente.setStatus(1);
        
        clienteService.create(nuevoCliente);
        nuevoCliente.setId(clienteService.last().getId()); 
        clienteList.add(nuevoCliente);
       
        RequestContext.getCurrentInstance().showMessageInDialog(
                new FacesMessage("Cliente " + nombre + " registrado "));
        
        resetFormulario();
       
    }
    
    
	public void resetFormulario() {
    	this.nombre = null;
        this.contacto = null;
        this.telefono = null;
        this.email = null;
        this.rfc = null;
        this.estado = null;
        this.municipio = null;
        this.localidad = null;
        this.codigopostal = null;
        this.asentamiento = null;
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
	
	public ArrayList<String> getClienteNombre(){
		nombres = new ArrayList<String>();
		for(int i = 0; i < clienteList.size(); i++) {
			nombres.add(clienteList.get(i).getNombre());
		}
		
		return nombres;
	}
	
	public ArrayList<Integer> getClienteId(){
		ids = new ArrayList<Integer>();
		for(int i = 0; i < clienteList.size(); i++) {
			ids.add(clienteList.get(i).getId());
		}
		
		return ids;
	}

}
