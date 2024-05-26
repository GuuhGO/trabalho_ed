package controller.csv;

import model.cliente.BaseCliente;
import model.cliente.ClienteFisico;
import model.cliente.ClienteJuridico;
import model.cliente.Endereco;


public class ClienteCsvController extends BaseCsvController<BaseCliente> {

    public ClienteCsvController() {}

    @Override
    public String getHeader() {
        return "cpf_cnpj;tipo;nome_fantasia;logradouro;numero;complemento;cep;telefone;email";
    }

    @Override
    public String getFileName() {
        return "cliente";
    }

    @Override
    public BaseCliente objectBuilder(String csvLine) throws Exception {
        String[] campos = csvLine.split(";");
        int length = campos.length;
        if(length != 9) {
            throw new Exception("Registro Inválido");
        }

        return switch (campos[1]) {
            case "Físico" -> buildClienteFisico(campos);
            case "Jurídico" -> buildClienteJuridico(campos);
            default -> throw new Exception("Registro Inválido");
        };
    }

    private ClienteJuridico buildClienteJuridico(String[] campos) throws Exception {
        String cnpj = campos[0];
        String fantasia = campos[2];
        String telefone = campos[7];
        String email = campos[8];
        String logradouro = campos[3];
        int numero = Integer.parseInt(campos[4]);
        String complemento = campos[5];
        String cep = campos[6];

        Endereco endereco = enderecoBuilder(logradouro, numero, complemento, cep);

        return new ClienteJuridico(fantasia, cnpj, telefone, email, endereco);
    }

    private ClienteFisico buildClienteFisico(String[] campos) throws Exception {
        String cpf = campos[0];
        String nome = campos[2];
        String telefone = campos[7];
        String logradouro = campos[3];
        int numero = Integer.parseInt(campos[4]);
        String cep = campos[6];
        String complemento = campos[5];

        Endereco endereco = enderecoBuilder(logradouro,numero, complemento, cep);

        return new ClienteFisico(nome, cpf, telefone, endereco);
    }

    private Endereco enderecoBuilder(String logradouro, int numero, String complemento, String cep) throws Exception {
        logradouro = logradouro.equals("null") ? "Não Encontrado" : logradouro;
        complemento = complemento.equals("null") ? "" : complemento;
        cep = cep.equals("null") ? "Não Encontrado" : cep;
        return new Endereco(logradouro, numero, complemento, cep);
    }
}
