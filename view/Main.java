package view;

import model.Cliente;

public class Main {
	public static void main(String[] args) {
		Cliente cliente1 = new Cliente("Gustavo", "12345", "91234");
		cliente1.setEndereco("Avenida Águia de Haia", 100, "A", "08123-456");
		String csv = cliente1.getCsv();
		System.out.println(csv);
	}
}