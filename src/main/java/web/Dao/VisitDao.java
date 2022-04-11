package web.Dao;

import org.springframework.stereotype.Repository;
import web.Entry.Visit;

import java.util.List;

@Repository
public interface VisitDao {
    public List<Visit> getVisitList();
}
