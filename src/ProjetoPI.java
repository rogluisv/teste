import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ProjetoPI {
	static int medico;
	public static void main(String[] args) {	
		System.out.println(" ---- Sistema Iniciando ----");
		menuPrincipal();
		System.out.println(" ---- Sistema Encerrado ---- ");
}
	// ------------- MENU INICIAL -------------
	public static void menuPrincipal(){
		int resposta;
		Scanner s = new Scanner(System.in);
		System.out.println("Informe quantos médicos está atendendo no momento: ");
		medico = Integer.parseInt(s.nextLine());
		System.out.println("[1] - Tela de Pacientes(inicial)");
		System.out.println("[2] - Tela de Triagem(AT)");
		System.out.println("[3] - Tela do Médico");
		System.out.println("[4] - Tela de espera(Principal)");
		System.out.println("Opção: ");
		resposta = Integer.parseInt(s.nextLine());
		switch(resposta) {
	case 1:
		do {
		System.out.println("[1] - Cadastrar Paciente em espera");
		System.out.println("[2] - Exibir Pacientes em espera");
		System.out.println("[3] - Alterar dados do Paciente em espera");
		System.out.println("[4] - Excluir Paciente em espera(caso ainda não tenha feito a triagem");
		System.out.println("[5] - Sair");
		System.out.println("Opção: ");
		resposta = Integer.parseInt(s.nextLine());
		switch(resposta){
	case 1:
		cadastrarPac();
			break;
	case 02:
		consultarPac();
			break;
	case 03:
		alterarPac();
			break;
	case 04:
		excluirPac();
			break;
		}
	}while(resposta != 5); break;
	case 2:
		do {
			System.out.println("[1] - Efetuar Triagem");
			System.out.println("[2] - Exibir pacientes que aguardam triagem");
			System.out.println("[3] - Exibir pacientes que efetuaram triagem");
			System.out.println("[4] - Alterar dados da triagem do paciente");
			System.out.println("[5] - Sair");
			System.out.println("Opção: ");
			resposta = Integer.parseInt(s.nextLine());
			switch(resposta){
		case 1:
			efetuarTriagem();
				break;
		case 2:
			exibirPacienteAguardTriagem();
			break;
		case 3: 
				exibirPacienteTriagem();
				break;
		case 4:
			alterarPacTriagem();
				break;
			}
	}while(resposta != 5); break;
	case 3:
		do {
			System.out.println("[1] - Exibir pacientes em espera do atendimento");
			System.out.println("[2] - Paciente consultado");
			System.out.println("[3] - Paciente se evadiu da consulta");
			System.out.println("[4] - Sair");
			resposta = Integer.parseInt(s.nextLine());
			switch(resposta){
		case 1:
			exibirPacientesMedico();
				break;
		case 2:
			pacienteConsultado();
				break;
		case 3:
			pacienteEvadiu();
			break;
			}
	}while(resposta != 4); break;
		case 4:
			telaPrincipal();
			break;
}
		
	}
	// ------------- PACIENTE EVADIU DA CONSULTA ------------
	public static void pacienteEvadiu() {
		Connection c = getConnection();
		System.out.println("---------- Paciente se evadiu da consulta ----------");
		String sql = "DELETE PACIENTE, TRIAGEM FROM PACIENTE INNER JOIN TRIAGEM ON PACIENTE.IDPACIENTE = TRIAGEM.IDPACIENTE WHERE IDPACIENTE =?";
		PreparedStatement ps = null;
		try {
		ps = c.prepareStatement(sql);
		Scanner s = new Scanner(System.in);
		System.out.println("Informe o código do paciente: ");
			int idPaciente = Integer.parseInt(s.nextLine());
		ps.setInt(1, idPaciente);
		ps.executeUpdate();
		System.out.println("Operação realizada com sucesso");
		} catch (SQLException e) {
			System.out.println("erro ao excluir do banco"+ e.getMessage());
		}finally {
			try {
			ps.close();
			c.close();
		}catch (SQLException e) {
			System.out.println("erro ao finalizar banco: "+ e.getMessage());
		}
	}	
	}
	// ------------- EXIBIR PACIENTES AGUARDANDO TRIAGEM ------------
	public static void exibirPacienteAguardTriagem() {
		System.out.println("---------- Exibir lista de Pacientes Aguardando Triagem ----------");
		Connection c = getConnection();
		String sql = "SELECT IDPACIENTE, NOME FROM PACIENTE WHERE IDPACIENTE NOT IN (SELECT IDPACIENTE FROM TRIAGEM)";
		ResultSet rs;
		PreparedStatement ps = null;
		try {
		ps = c.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()){
			System.out.printf("%3d \t", rs.getInt("IDPACIENTE")); 
			System.out.printf("%-25s \t", rs.getString("NOME")); 
			System.out.println();
		}
			}catch(SQLException e) {
		System.out.println("Erro ao exibir pacientes: " + e.getMessage());
		}finally {
		try {
		ps.close();
		c.close();
			}catch (SQLException e) {
		System.out.println("erro ao finalizar banco: "+ e.getMessage());
		}
	}		
}
	// ------------- TELA PRINCIPAL -------------
	public static void telaPrincipal() {
		int resultado;
		System.out.println("---------- Exibindo Tela Principal ----------");
		do {
		Connection c = getConnection();
		String sql = "SELECT NOME ,GRAU_URGENCIA FROM TRIAGEM join PACIENTE ON PACIENTE.IDPACIENTE=TRIAGEM.IDPACIENTE ORDER BY GRAU_URGENCIA DESC";
		ResultSet rs;
		PreparedStatement ps = null;
		try {
		ps = c.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()){
			System.out.printf("%-25s \t", rs.getString("NOME")); 
			System.out.printf("%-25s \t",	rs.getInt("GRAU_URGENCIA"));
			//System.out.printf();
			
			System.out.println();
		}
			}catch(SQLException e) {
		System.out.println("Erro ao exibir pacientes: " + e.getMessage());
		}finally {
		try {
		ps.close();
		c.close();
			}catch (SQLException e) {
		System.out.println("erro ao finalizar banco: "+ e.getMessage());
		}
	}Scanner s = new Scanner(System.in);
	
		System.out.println("[1] Finalizar");
		System.out.println("[2] Atualizar");
			resultado = Integer.parseInt(s.nextLine());
		}while(resultado != 1);
	}
	// ------------- PACIENTE CONSULTADO -------------
	public static void pacienteConsultado() {
		Connection c = getConnection();
		System.out.println("---------- Paciente consultado ----------");
		String sql = "DELETE FROM TRIAGEM WHERE IDTRIAGEM=?";
		PreparedStatement ps = null;
		try {
		ps = c.prepareStatement(sql);
		Scanner s = new Scanner(System.in);
		System.out.println("Informe o código da Triagem do paciente consultado: ");
			int idTriagem = Integer.parseInt(s.nextLine());
		ps.setInt(1, idTriagem);
		ps.executeUpdate();
		System.out.println("Atendimento realizado");
		} catch (SQLException e) {
			System.out.println("erro ao inserir no banco"+ e.getMessage());
		}finally {
			try {
			ps.close();
			c.close();
		}catch (SQLException e) {
			System.out.println("erro ao finalizar banco: "+ e.getMessage());
		}
	}	
}
	// ------------- EXIBIR PACIENTES EM ESPERA DO ANTENDIMENTO -------------
	public static void exibirPacientesMedico() {
		System.out.println("---------- Exibir pacientes em espera ----------");
		Connection c = getConnection();
		String sql = "SELECT IDTRIAGEM ,NOME ,GRAU_URGENCIA, SINTOMAS FROM TRIAGEM JOIN PACIENTE ON PACIENTE.IDPACIENTE=TRIAGEM.IDPACIENTE order by GRAU_URGENCIA DESC";
		ResultSet rs;
		PreparedStatement ps = null;
		try {
		ps = c.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()){
			System.out.printf("%3d \t", rs.getInt("IDTRIAGEM")); 
			System.out.printf("%-25s \t", rs.getString("NOME")); 
			System.out.printf("%-25s \t",	rs.getInt("GRAU_URGENCIA"));
			System.out.printf("%-25s \t",	rs.getString("SINTOMAS"));
			System.out.println();
		}
			}catch(SQLException e) {
		System.out.println("Erro ao exibir pacientes: " + e.getMessage());
		}finally {
		try {
		ps.close();
		c.close();
			}catch (SQLException e) {
		System.out.println("erro ao finalizar banco: "+ e.getMessage());
		}
	}
}
	// ------------- ALTERAR DADOS DA TRIAGEM DO PACIENTE -------------
	public static void alterarPacTriagem() {
		Connection c = getConnection();
		Scanner s = new Scanner(System.in);
		Triagem triag = new Triagem();
		System.out.println("---------- Alterar dados da Triagem do Paciente ----------");
		System.out.println("Informe o código da triagem que deseja alterar: ");
		triag.idTriagem = Integer.parseInt(s.nextLine());
		String sql = "UPDATE TRIAGEM SET GRAU_URGENCIA=?,SINTOMAS=? WHERE IDTRIAGEM=?";
		PreparedStatement ps = null;
		try {
		ps = c.prepareStatement(sql);
		System.out.println("Informe o Grau de urgencia: ");
			triag.Grau_urgencia = Integer.parseInt(s.nextLine());
		System.out.println("Informe os sintomas");
		triag.Sintomas = s.nextLine();
		ps.setInt(1, triag.Grau_urgencia);
		ps.setString(2, triag.Sintomas);
		ps.setInt(3,   triag.idTriagem);
		ps.executeUpdate();
		System.out.println("Dados da triagem alterado com sucesso");
		} catch (SQLException e) {
			System.out.println("erro ao Alterar"+ e.getMessage());
		}finally {
			try {
			ps.close();
			c.close();
		}catch (SQLException e) {
			System.out.println("erro ao finalizar banco: "+ e.getMessage());
		}
	}
}
	// ------------- EXIBIR PACIENTES DA TRIAGEM -------------
	public static void exibirPacienteTriagem() {
		System.out.println("---------- Exibir lista de Pacientes ----------");
		Connection c = getConnection();
		String sql = "SELECT IDTRIAGEM ,NOME ,GRAU_URGENCIA FROM TRIAGEM join PACIENTE ON PACIENTE.IDPACIENTE=TRIAGEM.IDPACIENTE ORDER BY GRAU_URGENCIA DESC";
		ResultSet rs;
		PreparedStatement ps = null;
		try {
		ps = c.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()){
			System.out.printf("%3d \t", rs.getInt("IDTRIAGEM")); 
			System.out.printf("%-25s \t", rs.getString("NOME")); 
			System.out.printf("%-25s \t",	rs.getInt("GRAU_URGENCIA"));
			
			System.out.println();
		}
			}catch(SQLException e) {
		System.out.println("Erro ao exibir pacientes: " + e.getMessage());
		}finally {
		try {
		ps.close();
		c.close();
			}catch (SQLException e) {
		System.out.println("erro ao finalizar banco: "+ e.getMessage());
		}
	}
}
	// ------------- EFETUAR TRIAGEM -----------
	public static void efetuarTriagem() {
		String d;
		System.out.println("---------- Efetuar Triagem ----------");
		PreparedStatement ps = null;
		Connection c = getConnection();
		String sql = "INSERT INTO TRIAGEM(IDTRIAGEM,IDPACIENTE,GRAU_URGENCIA,SINTOMAS) VALUES(?,?,?,?)";
		try {
		ps = c.prepareStatement(sql);
		Triagem triag = new Triagem();
		Scanner s = new Scanner(System.in);
		System.out.println("Informe o Código da Triagem: ");
			triag.idTriagem = Integer.parseInt(s.nextLine());
		System.out.println("Informe o Código do paciente: ");
			triag.idPaciente = Integer.parseInt(s.nextLine());
		System.out.println("Informe o Grau de urgencia([1] - [2] - [3]");
			triag.Grau_urgencia = Integer.parseInt(s.nextLine());
		System.out.println("Infome os Sintomas do paciente");
		triag.Sintomas = s.nextLine();
		ps.setInt(1, triag.idTriagem);
		ps.setInt(2, triag.idPaciente);
		ps.setInt(3, triag.Grau_urgencia);
		ps.setString(4, triag.Sintomas);
		ps.execute();
		System.out.println("Triagem Efetuada!");
		} catch (SQLException e) {
			System.out.println("erro ao inserir"+ e.getMessage());
		}finally {
			try {
			ps.close();
			c.close();
		}catch (SQLException e) {
			System.out.println("erro ao finalizar banco: "+ e.getMessage());
		}
	}		
}
	// ------------- ALTERAR PACIENTE -----------
	public static void alterarPac() {
		Connection c = getConnection();
		System.out.println("---------- Alterar dados do paciente ----------");
		System.out.println("Informe o código do paciente que deseja alterar: ");
		String sql = "UPDATE PACIENTE SET NOME=?,CPF=?,NASCIMENTO=? WHERE IDPACIENTE=?";
		PreparedStatement ps = null;
		try {
		ps = c.prepareStatement(sql);
		Paciente pacient = new Paciente();
		Scanner s = new Scanner(System.in);
		System.out.println("Informe o Código do paciente: ");
			pacient.idPaciente = Integer.parseInt(s.nextLine());
		System.out.println("Informe o nome do paciente: ");
			pacient.Nome = s.nextLine();
		System.out.println("Informe o CPF do paciente");
			pacient.CPF = s.nextLine();
		System.out.println("Infome o nascimento do paciente(sem '/', ex: 14062020");
			pacient.Nascimento = s.nextLine();
		ps.setInt(4, pacient.idPaciente);
		ps.setString(1, pacient.Nome);
		ps.setString(2, pacient.CPF);
		ps.setString(3,   pacient.Nascimento);
		ps.executeUpdate();
		System.out.println("Dados do paciente Alterado com sucesso");
		} catch (SQLException e) {
			System.out.println("erro ao Alterar"+ e.getMessage());
		}finally {
			try {
			ps.close();
			c.close();
		}catch (SQLException e) {
			System.out.println("erro ao finalizar banco: "+ e.getMessage());
		}
	}
}
	// ------------- EXCLUIR PACIENTE -----------
	public static void excluirPac() {
		Connection c = getConnection();
		System.out.println("---------- Excluir paciente ----------");
		String sql = "DELETE FROM PACIENTE WHERE IDPACIENTE=?";
		PreparedStatement ps = null;
		try {
		ps = c.prepareStatement(sql);
		Scanner s = new Scanner(System.in);
		System.out.println("Informe o código do paciente que deseja Excluir: ");
		int idPaciente = Integer.parseInt(s.nextLine());
		ps.setInt(1, idPaciente);
		ps.executeUpdate();
		System.out.println("Paciente excluido com sucesso");
		} catch (SQLException e) {
			System.out.println("erro ao Excluir"+ e.getMessage());
		}finally {
			try {
			ps.close();
			c.close();
		}catch (SQLException e) {
			System.out.println("erro ao finalizar banco: "+ e.getMessage());
		}
	}	
}
	// ------------- CONSULTAR PACIENTE -----------
	public static void consultarPac() {
		System.out.println("---------- Listar pacientes ----------");
		Connection c = getConnection();
		String sql = "SELECT * FROM PACIENTE order by IDPACIENTE";
		ResultSet rs;
		PreparedStatement ps = null;
		try {
		ps = c.prepareStatement(sql);
		rs = ps.executeQuery();
		while (rs.next()){
			System.out.printf("%3d \t", rs.getInt("IDPACIENTE")); 
			System.out.printf("%-30s \t", rs.getString("NOME")); 
			System.out.printf("%-30s \t",	rs.getString("CPF"));
			System.out.printf("%-30s \t", rs.getString("NASCIMENTO")); 
			System.out.println();
		}
			}catch(SQLException e) {
		System.out.println("Erro ao exibir pacientes: " + e.getMessage());
		}finally {
		try {
		ps.close();
		c.close();
			}catch (SQLException e) {
		System.out.println("erro ao finalizar banco: "+ e.getMessage());
		}
	}	
}
	// ------------- CADASTRAR PACIENTE  -----------
	public static void cadastrarPac() {
		String d;
		System.out.println("---------- Cadastrar paciente ----------");
		PreparedStatement ps = null;
		Connection c = getConnection();
		String sql = "INSERT INTO PACIENTE(IDPACIENTE,NOME,CPF,NASCIMENTO) VALUES(?,?,?,?)";
		try {
		ps = c.prepareStatement(sql);
		Paciente pacient = new Paciente();
		Scanner s = new Scanner(System.in);
		System.out.println("Informe o Código do paciente: ");
			pacient.idPaciente = Integer.parseInt(s.nextLine());
		System.out.println("Informe o nome do paciente: ");
			pacient.Nome = s.nextLine();
		System.out.println("Informe o CPF do paciente");
			pacient.CPF = s.nextLine();
		System.out.println("Infome o nascimento do paciente((sem '/', ex: 14062020)");
			d = s.nextLine();	
			pacient.Nascimento = d;
		ps.setInt(1, pacient.idPaciente);
		ps.setString(2, pacient.Nome);
		ps.setString(3, pacient.CPF);
		ps.setString(4, pacient.Nascimento);
		ps.execute();
		System.out.println("Paciente Cadastrado com sucesso");
		} catch (SQLException e) {
			System.out.println("erro ao inserir"+ e.getMessage());
		}finally {
			try {
			ps.close();
			c.close();
		}catch (SQLException e) {
			System.out.println("erro ao finalizar banco: "+ e.getMessage());
		}
	}
}
	// -------------- GERAR CONEXÃO ------------------
	public static Connection getConnection(){
		Connection c= null;	
		String url = "jdbc:oracle:thin:@localhost:1521: XE";
		String usuario = "system";
		String senha = "aula";
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		c = DriverManager.getConnection(url, usuario, senha);
	} catch (SQLException e) {
		System.out.println("Erro ao conectar: " + e.getMessage());
	} catch (ClassNotFoundException e) {
		System.out.println("Erro ao registrar: " + e.getMessage());
		}
		return c;
	}
}
// -------------- TABELAS PACIENTE E TRIAGEM ----------------
class Paciente {
public int idPaciente;
public String Nome;
public String CPF;
public String Nascimento;
}
class Triagem {
public int idTriagem;
public int idPaciente;
public String Sintomas;
public int Grau_urgencia;
}