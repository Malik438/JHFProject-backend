package org.example.ecommerce.model.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value = """
      select t from token t inner join User u\s
      on t.user.userId = u.userId\s
      where u.userId = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Long id);


    @Query(value = """
      select t from token t inner join Supplier u\s
      on t.supplier.supplierId = u.supplierId\s
      where u.supplierId = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenBySupplier(Long id);

    Optional<Token> findByToken(String token);
}
