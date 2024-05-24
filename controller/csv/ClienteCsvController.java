package controller.csv;

import model.cliente.BaseCliente;
import model.cliente.ClienteFisico;
import model.cliente.ClienteJuridico;
import model.cliente.Endereco;


public class ClienteCsvController extends BaseCsvController<BaseCliente> {

    public ClienteCsvController(String dirPath, String fileName, String header) {
        super(dirPath, fileName, header);
    }

    @Override
    public BaseCliente objectBuilder(String[] campos) throws Exception {
        if (campos.length < 5) {
            throw new Exception("Registro Inválido");
        }
        // Cria o cliente de acordo com seu tipo
        return switch (campos[1]) {
            case "Jurídico" -> clienteJuridicoBuilder(campos);
            case "Físico" -> clienteFisicoBuilder(campos);
            default -> throw new Exception("Registro Inválido");
        };
    }

    private BaseCliente clienteFisicoBuilder(String[] campos) throws Exception {
        int length = campos.length;

        String   cpf      = campos[0];
        String   nome     = campos[1];
        Endereco endereco = !campos[2].equals("null") ? enderecoBuilder(campos) : null; // TODO: pergunta: devemos registrar o cliente sem endereço caso ele esteja errado?
        // validar se a quantidade de campos bate com o correto
        if ((endereco != null && length != 7) || (endereco == null & length != 4)) {
            throw new Exception("Registro Inválido");
        }
        String telefone = campos[length - 1];

        return new ClienteFisico(nome, cpf, telefone, endereco);
    }

    private BaseCliente clienteJuridicoBuilder(String[] campos) throws Exception {
        int length = campos.length;

        String   cnpj     = campos[0];
        String   fantasia = campos[1];
        Endereco endereco = !campos[2].equals("null") ? enderecoBuilder(campos) : null; // TODO: pergunta: devemos registrar o cliente sem endereço caso ele esteja errado?
        // validar se a quantidade de campos bate com o correto
        if ((endereco != null && length != 8) || (endereco == null & length != 5)) {
            throw new Exception("Registro Inválido");
        }
        String telefone = campos[campos.length - 2];
        String email    = campos[campos.length - 1];

        return new ClienteJuridico(fantasia, cnpj, telefone, email, endereco);
    }

    private Endereco enderecoBuilder(String[] campos) throws Exception {
        if (campos[2].equals("null")) {
            return null;
        }

        String logradouro = campos[2];
        int numero;
        try {
            numero = Integer.parseInt(campos[3]);
        } catch (NumberFormatException e) {
            throw new Exception("Endereço inválido");
        }

        String complemento = !campos[4].equals("null") ? campos[4] : "";
        String cep = campos[5];

        return new Endereco(logradouro, numero, complemento, cep);
    }
}
