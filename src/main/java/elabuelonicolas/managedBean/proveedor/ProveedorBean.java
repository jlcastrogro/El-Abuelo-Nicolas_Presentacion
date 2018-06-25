package elabuelonicolas.managedBean.proveedor;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import elabuelonicolas.bd.domain.Proveedor;
import elabuelonicolas.service.proveedor.ProveedorService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;


@Named
@ViewScoped
public class ProveedorBean {
	@Inject
	private ProveedorService proveedorService;
	private List<Proveedor> proveedorList;	
	private List<Proveedor> filteredProv;
	//Campos proveedor
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

    private ArrayList<String> proveedores;

	@PostConstruct
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
		System.out.println("Datos proveedorEdit: " + proveedor.getId());
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
	
	public String deleteAction(Proveedor order){   
		proveedorList.remove(order);
		proveedorService.delete(order.getId());
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
        
    	//Insertar proveedor
        Proveedor nuevoProveedor = new Proveedor();
        nuevoProveedor.setNombre(nombre);
        nuevoProveedor.setContacto(contacto);
        nuevoProveedor.setTelefono(telefono);
        nuevoProveedor.setEmail(email);
        nuevoProveedor.setRfc(rfc);
        nuevoProveedor.setEstado(estado);
        nuevoProveedor.setMunicipio(municipio);
        nuevoProveedor.setLocalidad(localidad);
        nuevoProveedor.setCodigopostal(Integer.parseInt(codigopostal));
        nuevoProveedor.setAsentamiento(asentamiento);
        nuevoProveedor.setCalle(calle);
        nuevoProveedor.setNumero(Integer.parseInt(numero));
        nuevoProveedor.setStatus(1);
        
        proveedorService.create(nuevoProveedor);
        nuevoProveedor.setId(proveedorService.last().getId()); 
        proveedorList.add(nuevoProveedor);
       
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Proveedor " + nombre + " registrado "));
        
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
		Proveedor proveedor = ((Proveedor) event.getObject());
		System.out.println("Datos proveedorSelect: " + proveedor.getId());
		proveedorService.update(proveedor); 
		
		FacesMessage msg = new FacesMessage("Proveedor seleccionado", proveedor.getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void onRowUnselect(UnselectEvent event) {
		Proveedor proveedor = ((Proveedor) event.getObject());
		System.out.println("Datos proveedorUnselect: " + proveedor.getId());
		proveedorService.update(proveedor); 
		
		FacesMessage msg = new FacesMessage("Proveedor deseleccionado", proveedor.getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public List<Proveedor> getFilteredProveedor(){
		return filteredProv;
	}
	
	public void setFilteredProveedor(List<Proveedor> filteredProv) {
		this.filteredProv = filteredProv;
	}
	
	public ArrayList<String> getProveedorNombre(){
		proveedores = new ArrayList<String>();
		for(int i = 0; i < proveedorList.size(); i++) {
			proveedores.add(proveedorList.get(i).getNombre());
		}
		
		return proveedores;
	}
}
