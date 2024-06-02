package controller;

import controller.csv.ItemCompraCsvController;
import datastrucures.genericList.List;
import model.ICsv;
import model.ItemCompra;
import view.TelaEclipse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComprasController implements ActionListener {
    private final ItemCompraCsvController DB_COMPRAS = new ItemCompraCsvController();
    private final TelaEclipse SCREEN;

    public ComprasController(TelaEclipse tela) {
        this.SCREEN = tela;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String actionCommand = event.getActionCommand();
        JLabel lblError = SCREEN.getLblErrorSalesList();
        try {
            if(actionCommand.equals("PESQUISAR")) {
                pesquisar();
                lblError.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
            SCREEN.printError(lblError, e);
        }
    }

    private void pesquisar() throws Exception {
        JTextField textField = SCREEN.getTfSearchSales();
        JLabel lblSalesTotal = SCREEN.getLblSaleTotal();
        String search = textField.getText();
        if (search == null || search.isBlank()) {
            SCREEN.loadSalesTable();
            lblSalesTotal.setText("");
            return;
        }

        double totalValue = 0;
        List<ICsv> result = new List<>();
        var list = DB_COMPRAS.get();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ICsv item = list.get(i);
            if (item.getCsvId().equals(search)) {
                totalValue += ((ItemCompra) item).getPRODUTO().getValor();
                result.addLast(item);
            }
        }

        SCREEN.carregarDados(SCREEN.getSalesTable(), DB_COMPRAS.getHeader(), result);
        lblSalesTotal.setText(String.format("TOTAL VENDA: %06.2f", totalValue));
    }
}
