package com.example.exchangeApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.exchangeApp.model.Transaction;
import com.example.exchangeApp.dto.TransactionInfoDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


@Repository
public interface transactionRepo extends JpaRepository<Transaction, Long>{

    @Query(value = "SELECT COALESCE(SUM(transaction_amount), 0) FROM transactions WHERE user_source_id = (SELECT id FROM users WHERE email = :email)", nativeQuery = true)
	Double findSommeEnvoisByUserEmail(@Param("email") String email);

	@Query(value = "SELECT COALESCE(SUM(transaction_amount), 0) FROM transactions WHERE user_destination_id = (SELECT id FROM users WHERE email = :email)", nativeQuery = true)
	Double findSommeReceptionsByUserEmail(@Param("email") String email);

	@Query(value = "SELECT t.transaction_amount, t.transaction_date_time, CASE WHEN u.id = t.user_source_id THEN 'Expediteur' WHEN u.id = t.user_destination_id THEN 'Destinataire' ELSE 'Autre' END AS statut, CASE WHEN u.id = t.user_source_id THEN ud.email ELSE us.email END AS userEmail, CASE WHEN u.id = t.user_source_id THEN t.devise_destination ELSE t.devise_source END AS transactionCurrency FROM transactions t JOIN users u ON u.id = :userId AND (u.id = t.user_source_id OR u.id = t.user_destination_id) JOIN users ud ON ud.id = t.user_destination_id JOIN users us ON us.id = t.user_source_id", nativeQuery = true)
	List<Object[]>  findTransactionsInfoByUserId(@Param("userId") Long userId);

}


