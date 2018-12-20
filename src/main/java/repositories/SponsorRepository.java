package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsor;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {

    @Query("select s from Sponsor s join s.userAccount u where u.username = ?1")
    public Sponsor getSponsorByUsername(String username);
}
