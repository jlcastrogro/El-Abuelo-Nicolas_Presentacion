package elabuelonicolas.managedBean.venta;

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

import elabuelonicolas.bd.domain.Cliente;
import elabuelonicolas.bd.domain.Venta;
import elabuelonicolas.service.venta.VentaService;

@Named
@ViewScoped
public class VentaBean {
	@Inject
	VentaService ventasService;
	private List<Venta> ventasList;
	private List<Venta> filteredVen;
	
	private ArrayList<String> cantidadList = new ArrayList<String>();
	private int idCliente;
	private String cantidad = "0";
	private double total = 0.0;
	private double totalReal = 0.0;
	private double ganancia;
	

	@PostConstruct
	public List<Venta> getVentaList() {
		if (ventasList == null)
			setVentaList(ventasService.findAll());
		
		return ventasList;
	}
	
	private void setVentaList(List<Venta> ventasList) {
		this.ventasList = ventasList;
	}

	public void onRowEdit(RowEditEvent event) {
		Venta ventas = ((Venta) event.getObject());
		System.out.println("Datos de Venta: " + ventas.getId());
		ventasService.update(ventas);

		FacesMessage msg = new FacesMessage("Venta editada", ventas.getId()
				.toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edición cancelada",
				((Venta) event.getObject()).getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		System.out.println("Verifica: " + newValue);

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Venta modificada", "Antes: " + oldValue + ", Ahora: "+ newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}
	
	public String deleteAction(Venta order){   
		ventasList.remove(order);
		ventasService.delete(order.getId());
		return null;
	}
	
	public void onRowSelect(SelectEvent event) {
		Venta venta = ((Venta) event.getObject());
		System.out.println("Datos ventaSelect: " + venta.getId());
		ventasService.update(venta); 
		
		FacesMessage msg = new FacesMessage("Venta seleccionada", venta.getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public void onRowUnselect(UnselectEvent event) {
		Venta venta = ((Venta) event.getObject());
		System.out.println("Datos proveedorUnselect: " + venta.getId());
		ventasService.update(venta); 
		
		FacesMessage msg = new FacesMessage("Venta deseleccionada", venta.getId().toString());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public List<Venta> getFilteredVenta(){
		return filteredVen;
	}
	
	public void setFilteredVenta(List<Venta> filteredVen) {
		this.filteredVen = filteredVen;
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
    
    public void save(List<Cliente> clienteList, ArrayList<Double> costos, ArrayList<Double> costosReal) {
    	
    	getIdCliente(option, clienteList);
    	calcularTotal(costos, costosReal);
    	java.util.Date myDate = new java.util.Date();
    	java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());

    	//Insertar a tabla venta
    	Venta nuevaVenta = new Venta();
    	nuevaVenta.setIdcliente(idCliente);
    	nuevaVenta.setFecha(sqlDate);
    	nuevaVenta.setTotal(total);
    	nuevaVenta.setTotalreal(totalReal);
    	nuevaVenta.setGanancia(ganancia);
    	
    	ventasService.create(nuevaVenta);
    	nuevaVenta.setId(ventasService.last().getId()); 
        ventasList.add(nuevaVenta);
    	//Insertar a tabla listaventa
    	
    	FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Venta a "+ option + " registrada - Total: " + total));
    	
    	resetFormulario();
    }
    
	public void resetFormulario() {
    	this.cantidad = "0";
    	cantidadList.clear();
    	this.total = 0.0;
    	this.totalReal = 0.0;
    	this.ganancia = 0.0;
    	this.idCliente = 0;
    }
	
	public void getIdCliente(String option, List<Cliente> clienteList ) {
		for(int i= 0; i < clienteList.size(); i++ ) {
			if(clienteList.get(i).getNombre().equals(option)) {
				idCliente = clienteList.get(i).getId();
			}
		}
	}
	
	public void calcularTotal(ArrayList<Double> costos, ArrayList<Double> costosReal) {
	
		for(int i = 0; i < costos.size(); i++) {
			total += Integer.parseInt(cantidadList.get(i)) * costos.get(i);
			totalReal += Integer.parseInt(cantidadList.get(i)) * costosReal.get(i);
		}
		
		ganancia = total - totalReal;
	}
}
