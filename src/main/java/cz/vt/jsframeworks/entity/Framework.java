package cz.vt.jsframeworks.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "framework", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"frameworkName","version"})
})
public class Framework {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @Column(name = "frameworkName", nullable = false)
    @NotBlank(message = "FrameworkName cannot be blank")
    private String frameworkName;

    @NonNull
    @Column(name = "version", nullable = false)
    @NotBlank(message = "Version cannot be blank")
    private String version;

    @NonNull   
    @Min(value = 1)   
    @Max(value = 10)
    @Column(name = "hypoLevel", nullable = false)   
    private Long hypeLevel;

    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "deprecationDate")
    private LocalDate deprecationDate;

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", frameworkName='" + getFrameworkName() + "'" +
            ", version='" + getVersion() + "'" +
            ", hypoLevel='" + getHypeLevel() + "'" +
            ", deprecationDate='" + getDeprecationDate() + "'" +
            "}";
    }

    

}
