package sp.senac.br.pet.model;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sp.senac.br.pet.SecurityConfig;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private int idUsuario;

    @CPF(message = "CPF inválido!")
    private String cpf;

    @NotBlank(message = "Preencha o nome!")
    @Size(max = 70)
    private String nome;

    @Transient
    @NotBlank(message = "Preencha o sobrenome!")
    private String sobrenome;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private LocalDate nascimento;

    @NotBlank(message = "Preencha o telefone!")
    private String telefone;

    @NotBlank(message = "Preencha o campo email!")
    @Email(message = "Email inválido!")
    private String email;

    /*@Transient
    private String cemail;*/

    private char sexo;

    @NotBlank(message = "Preencha a senha!")
    @Column(name = "senha")
    private String hashSenha;

    /*@Transient
    private String csenha;*/

    /**
     * tipoAcesso = 1 -> Acesso de Cliente
     * tipoAcesso = 2 -> Acesso de Funcionário de filial
     * tipoAcesso = 3 -> Acesso de Funcionário Gerente de filial
     * tipoAcesso = 4 -> Acesso de Funcionário Gerente Regional
     * tipoAcesso = 5 -> Acesso de Funcionário TI
     * tipoAcesso = 6 -> Acesso de Funcionário Backoffice
     * tipoAcesso = 7 -> Acesso de Funcionário RH
     */
    @Column(name = "tipoacesso")
    private int tipoAcesso;

    private int ativo;

    @OneToMany(mappedBy = "usuario")
    private Set<Endereco> enderecos;

    /*@AssertTrue(message = "As senhas não são iguais!")
    private boolean isValid(){
        return this.hashSenha.equals(this.csenha);
    }*/
    public Usuario() {
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public final void setSenha(String senha) {
        this.hashSenha =
                SecurityConfig.bcryptPasswordEncoder()
                        .encode(senha);
    }

    public String getHashSenha() {
        return hashSenha;
    }

    public void setHashSenha(String hashSenha) {
        this.hashSenha = hashSenha;
    }

    /*public String getCsenha() {
        return csenha;
    }

    public final void setCsenha(String csenha) {
        this.csenha = SecurityConfig.bcryptPasswordEncoder()
                .encode(csenha);
    }*/

    public int getTipoAcesso() {
        return tipoAcesso;
    }

    public void setTipoAcesso(int tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return getHashSenha();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
