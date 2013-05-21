package co.ohba.getstatus.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Loadout {

    @Id
    private Long id;
    private String name;
    private Long geekId;
    private List<String> clouds;
    private Date created;
    private Date updated;

}
