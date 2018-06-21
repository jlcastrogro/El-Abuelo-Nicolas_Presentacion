package elabuelonicolas.managedBean.producto;

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

import elabuelonicolas.bd.domain.Producto;
import elabuelonicolas.service.producto.ProductoService;

@Named
public class ProductoBean {
	@Inject
	ProductoService productoService;
	private List<Producto> productoList;
	private List<Producto> filteredProd;
	//Campos producto
	private String tipo;
    private String marca;
    private String costocompra;
    private String costoventa;
    private String existencia;
    
    private ArrayList<String> tipos;
    private ArrayList<Double> costos;
    private ArrayList<Double> costosReal;
    private ArrayList<Integer> ids;

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
	
	public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getMarca() {
        return marca;
    }
    
    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    public String getCostocompra() {
        return costocompra;
    }
    
    public void setCostocompra(String costocompra) {
        this.costocompra = costocompra;
    }
    
    public String getCostoventa() {
        return costoventa;
    }
    
    public void setCostoventa(String costoventa) {
        this.costoventa = costoventa;
    }

    public String getExistencia() {
        return existencia;
    }
    
    public void setExistencia(String existencia) {
        this.existencia = existencia;
    }
    
    public void save() {
        
    	//Insertar producto
        Producto nuevoProducto = new Producto();
        nuevoProducto.setTipo(tipo);
        nuevoProducto.setMarca(marca);
        nuevoProducto.setCostocompra(Double.parseDouble(costocompra));
        nuevoProducto.setCostoventa(Double.parseDouble(costoventa));
        nuevoProducto.setExistencia(Integer.parseInt(existencia));
        nuevoProducto.setStatus(1);
        
        productoService.create(nuevoProducto);
        nuevoProducto.setId(productoService.last().getId()); 
        productoList.add(nuevoProducto);
       
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Producto " + tipo + " registrado "));
        
        resetFormulario();   
    }
    
	public void resetFormulario() {
    	this.tipo = null;
        this.marca = null;
        this.costocompra = null;
        this.costoventa = null;
        this.existencia = null;
    }
	
	public void onRowSelect(SelectEvent event) {
		Producto producto = ((Producto) event.getObject());
		System.out.println("Datos proveedorSelect: " + producto.getId());
		productoService.update(producto); 
		
		FacesMessage msg = new FacesMessage("Producto seleccionado", producto.getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void onRowUnselect(UnselectEvent event) {
		Producto producto = ((Producto) event.getObject());
		System.out.println("Datos proveedorUnselect: " + producto.getId());
		productoService.update(producto); 
		
		FacesMessage msg = new FacesMessage("Producto deseleccionado", producto.getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public List<Producto> getFilteredProducto(){
		return filteredProd;
	}
	
	public void setFilteredProducto(List<Producto> filteredProd) {
		this.filteredProd = filteredProd;
	}
	
	public ArrayList<String> getTipos(){
		getProductoList();
		tipos = new ArrayList<String>();
		for(int i = 0; i < productoList.size(); i++) {
			tipos.add(productoList.get(i).getTipo() + " " + productoList.get(i).getMarca());
		}
		
		return tipos;
	}
	
	//Costoventa
	public ArrayList<Double> getCostos(){
		getProductoList();
		costos = new ArrayList<Double>();
		for(int i = 0; i < productoList.size(); i++) {
			costos.add(productoList.get(i).getCostoventa());
		}
		
		return costos;
	}
	
	//Costocompra
	public ArrayList<Double> getCostosReal(){
		getProductoList();
		costosReal = new ArrayList<Double>();
		for(int i = 0; i < productoList.size(); i++) {
			costosReal.add(productoList.get(i).getCostocompra());
		}
		
		return costosReal;
	}
	
	public ArrayList<Integer> getIds(){
		getProductoList();
		ids = new ArrayList<Integer>();
		for(int i = 0; i < productoList.size(); i++) {
			ids.add(productoList.get(i).getId());
		}
		
		return ids;
	}

}