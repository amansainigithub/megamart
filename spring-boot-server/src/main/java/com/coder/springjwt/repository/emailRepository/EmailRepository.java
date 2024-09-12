package com.coder.springjwt.repository.emailRepository;

import com.coder.springjwt.models.props.Api_Props;
import com.coder.springjwt.payload.emailPayloads.EmailSendContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EmailSendContent, Long> {
}
