package com.joaopedroluz.cursomc.services;

import com.joaopedroluz.cursomc.domain.Cidade;
import com.joaopedroluz.cursomc.domain.Cliente;
import com.joaopedroluz.cursomc.domain.Cliente;
import com.joaopedroluz.cursomc.domain.Endereco;
import com.joaopedroluz.cursomc.domain.enums.Perfil;
import com.joaopedroluz.cursomc.domain.enums.TipoCliente;
import com.joaopedroluz.cursomc.dto.ClienteDTO;
import com.joaopedroluz.cursomc.dto.ClienteNewDTO;
import com.joaopedroluz.cursomc.repositories.ClienteRepository;
import com.joaopedroluz.cursomc.repositories.ClienteRepository;
import com.joaopedroluz.cursomc.repositories.EnderecoRepository;
import com.joaopedroluz.cursomc.security.UserSS;
import com.joaopedroluz.cursomc.services.exceptions.AuthorizationException;
import com.joaopedroluz.cursomc.services.exceptions.DataIntegrityViolationException;
import com.joaopedroluz.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository repo;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private BCryptPasswordEncoder pe;
    @Autowired
    private S3Service s3Service;
    @Autowired
    private ImageService imageService;

    @Value("${img.prefix.cliente.profile}")
    private String prefix;

    public List<Cliente> findAll() {
        return repo.findAll();
    }

    public Cliente find(Integer id) {
        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationException("Acesso negado");
        }

        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente findByEmail(String email) {
        UserSS user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
            throw new AuthorizationException("Acesso Negado");
        }
        Cliente obj = repo.findByEmail(email);
        if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName());
        }
        return obj;
    }

    public Cliente insert(Cliente obj) {
        obj.setId(null);
        obj = repo.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }

    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Não é possível excluir porque há pedidos relacionados");
        }
    }

    public Cliente fromDTO(ClienteDTO objDTO) {
        return new Cliente(objDTO.getNome(), objDTO.getEmail(), null, null, null);
    }

    public Cliente fromDTO(ClienteNewDTO objDTO) {
        Cliente cli = new Cliente(objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()), pe.encode(objDTO.getSenha()));
        Cidade cid = new Cidade();
        cid.setId(objDTO.getCidadeId());
        Endereco end = new Endereco(objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid);
        cli.addEndereco(end);
        cli.addTelfones(objDTO.getTelefone1());
        if (objDTO.getTelefone2() != null) {
            cli.addTelfones(objDTO.getTelefone2());
        }
        if (objDTO.getTelefone3() != null) {
            cli.addTelfones(objDTO.getTelefone3());
        }
        return cli;
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());

    }

    public URI uploadProfilePicture(MultipartFile multipartFile) {
        UserSS user = UserService.authenticated();
        if (user == null){
            throw new AuthorizationException("Acesso negado");
        }

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
        String fileName = prefix + user.getId() + ".jpg";

        return s3Service.uploadFile(fileName, imageService.getInputStream(jpgImage, "jpg"),  "image");
    }

}


