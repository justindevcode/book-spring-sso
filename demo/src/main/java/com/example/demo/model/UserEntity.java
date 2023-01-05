package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class UserEntity {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id; //유저 고유 id

	@Column(nullable = false)
	private String username; // 아이디로사용할 이름 이메일등등

	private String password; //나중에 깃허브연동으로 로그인하고하면 비번필요없어도됨 그래서 null허용 구현단계에서 직접 가입할때 필수로 설정

	private String role; //어드민,일반사용자등

	private String authProvider; // OAuth에서 사용할 유저정보제공자 ex 깃허브

}
