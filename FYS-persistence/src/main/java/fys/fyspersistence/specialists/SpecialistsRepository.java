package fys.fyspersistence.specialists;

import fys.fysmodel.Announcement;
import fys.fysmodel.Specialist;
import fys.fyspersistence.Repository;

public interface SpecialistsRepository extends Repository<Integer, Specialist> {
    public Specialist findSpecialistByUsername(String username);
}
