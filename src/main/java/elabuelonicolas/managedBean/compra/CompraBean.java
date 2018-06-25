package elabuelonicolas.managedBean.compra;

import java.util.ArrayList;
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
import elabuelonicolas.bd.domain.Listacompra;
import elabuelonicolas.bd.domain.Producto;
import elabuelonicolas.bd.domain.Proveedor;
import elabuelonicolas.service.compra.CompraService;
import elabuelonicolas.service.listacompra.ListacompraService;
import elabuelonicolas.service.producto.ProductoService;

@Named
@ViewScoped
public class CompraBean {
	@Inject
	CompraService compraService;
	@Inject
	ProductoService productoService;
	@Inject
	ListacompraService listacomprasService;
	private List<Compra> compraList;
	private List<Compra> filteredComp;
	
	private ArrayList<String> cantidadList = new ArrayList<String>();
	private int idProveedor;
	private String cantidad = "0";
	private double totalReal = 0.0;
	
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
	
	private String option;
    private List<String> options;

    
    public String getOption() {
        return option;
    }
 
    public void setOption(String option) {
        this.option = option;
    }
 
    public List<String> getOptions() {
        return options;
    }
 
    public void setOptions(List<String> options) {
        this.options = options;
    }
   
    public String getCantidad() {
        return cantidad;
    }
    
    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
        cantidadList.add(cantidad);
    }
    
    public void save(List<Proveedor> proveedorList, ArrayList<Double> costosReal, List<Producto> productoList, List<Listacompra> listacomprasList) {
    	
    	getIdProveedor(option, proveedorList);
    	calcularTotal(costosReal);
    	java.util.Date myDate = new java.util.Date();
    	java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
    	
    	Compra nuevaCompra = new Compra();
    	nuevaCompra.setIdproveedor(idProveedor);
    	nuevaCompra.setFecha(sqlDate);
    	nuevaCompra.setTotal(totalReal);
    	
    	compraService.create(nuevaCompra);
    	nuevaCompra.setId(compraService.last().getId()); 
    	nuevaCompra.setNombre(option);
        compraList.add(nuevaCompra);
        
        actualizarCantidad(productoList);
    	//Insertar a tabla listacompra
        insertarListacompra(compraService.last().getId(), productoList, cantidadList, costosReal, listacomprasList);
    	
    	FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Compra a "+ option + " registrada - Total: " + totalReal));
    	
    	resetFormulario();
    }
    
	public void resetFormulario() {
    	this.cantidad = "0";
    	cantidadList.clear();
    	this.totalReal = 0.0;
    	this.idProveedor = 0;
    }
	
	public void actualizarCantidad(List<Producto> productoList) {
		
		for(int i = 0; i < productoList.size(); i++) {
			int nuevaExistencia = productoList.get(i).getExistencia() + Integer.parseInt(cantidadList.get(i));
			productoService.updateExistencia(productoList.get(i).getId(), nuevaExistencia);
			productoList.get(i).setExistencia(nuevaExistencia);
		}
	}
	
	public void getIdProveedor(String option, List<Proveedor> proveedorList ) {
		for(int i= 0; i < proveedorList.size(); i++ ) {
			if(proveedorList.get(i).getNombre().equals(option)) {
				idProveedor = proveedorList.get(i).getId();
			}
		}
	}
	
	public void calcularTotal(ArrayList<Double> costosReal) {
		
		for(int i = 0; i < costosReal.size(); i++) {
			totalReal += Integer.parseInt(cantidadList.get(i)) * costosReal.get(i);
		}
	}
	
	public void insertarListacompra(int idCompra, List<Producto> productoList, ArrayList<String> cantidadList, ArrayList<Double> costosReal, List<Listacompra> listacomprasList) {
		double subtotal = 0.0;
		
		for(int i = 0; i < productoList.size(); i++) {
		
			if( !cantidadList.get(i).equals("0")) {
				Listacompra nuevaListacompra = new Listacompra();
			
				nuevaListacompra.setIdcompra(idCompra);
				nuevaListacompra.setIdproducto(productoList.get(i).getId());
				nuevaListacompra.setCantidad( Integer.parseInt(cantidadList.get(i)) );
				subtotal = Integer.parseInt(cantidadList.get(i)) * costosReal.get(i);
				nuevaListacompra.setSubtotal(subtotal);
				
				listacomprasService.create(nuevaListacompra);
				
				nuevaListacompra.setId(listacomprasService.last().getId()); 
				nuevaListacompra.setTipo(productoList.get(i).getTipo());
				nuevaListacompra.setMarca(productoList.get(i).getMarca());
				listacomprasList.add(nuevaListacompra);
			}
		}
	}
}
