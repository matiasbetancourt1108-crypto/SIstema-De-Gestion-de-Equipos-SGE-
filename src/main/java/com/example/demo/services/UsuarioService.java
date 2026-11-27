package com.example.demo.services;

import com.example.demo.models.Usuario;
import com.example.demo.repositories.UsuarioRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario save(Usuario usuario) {
        if (usuario.getContrasena() == null || usuario.getContrasena().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria");
        }
        // Nunca se guarda la contraseña en texto plano: se guarda su hash BCrypt
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> findByNombre(String nombre) {
        return usuarioRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public Optional<Usuario> login(String email, String contrasena) {
        if (email == null || contrasena == null) {
            return Optional.empty();
        }
        return usuarioRepository.findByEmail(email)
                .filter(usuario -> passwordEncoder.matches(contrasena, usuario.getContrasena()));
    }

    public Usuario update(Usuario usuario) {
        Usuario existingUsuario = usuarioRepository.findById(usuario.getId()).orElse(null);
        if (existingUsuario != null) {
            existingUsuario.setNombre(usuario.getNombre());
            existingUsuario.setEmail(usuario.getEmail());
            existingUsuario.setTelefono(usuario.getTelefono());
            // Solo se re-encripta si se envía una contraseña nueva
            if (usuario.getContrasena() != null && !usuario.getContrasena().isBlank()) {
                existingUsuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
            }
            return usuarioRepository.save(existingUsuario);
        }
        return null;
    }

    public Usuario patch(Integer id, Map<String, Object> updates) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            if (updates.containsKey("nombre")) {
                usuario.setNombre((String) updates.get("nombre"));
            }
            if (updates.containsKey("email")) {
                usuario.setEmail((String) updates.get("email"));
            }
            if (updates.containsKey("telefono")) {
                usuario.setTelefono((String) updates.get("telefono"));
            }
            if (updates.containsKey("contrasena")) {
                String contrasena = (String) updates.get("contrasena");
                if (contrasena != null && !contrasena.isBlank()) {
                    usuario.setContrasena(passwordEncoder.encode(contrasena));
                }
            }
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    public void deleteById(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
