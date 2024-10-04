package ro.siit.HRS.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "functions")
public class Function {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
