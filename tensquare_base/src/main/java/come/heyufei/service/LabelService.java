package come.heyufei.service;

import come.heyufei.dao.LabelDao;
import come.heyufei.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 标签业务逻辑类
 */
@Service
public class LabelService {
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部标签
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     * 根据ID查询标签
     */
    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    /**
     * 增加标签
     */
    public void add(Label label) {
        label.setId(idWorker.nextId() + "");//设置ID
        labelDao.save(label);
    }

    /**
     * 修改标签
     */
    public void update(Label label) {
        labelDao.save(label);
    }

    /**
     * 删除标签
     */
    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    /**
     * 可以根据 labelname / state / recommend查询
     */
    public List<Label> findSearch(Map searchMap){
        return labelDao.findAll(createSpecification(searchMap));
    }

    /**
     * 分页条件查询
     * 可以根据 labelname / state / recommend查询
     */
    public Page<Label> findSearch(Map searchMap, int page, int size){
        Specification<Label> specification = createSpecification(searchMap);
        PageRequest pageRequest = PageRequest.of(page-1,size);
        return labelDao.findAll(specification,pageRequest);
    }


    private Specification<Label> createSpecification(Map searchMap) {
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //单独每一条查询条件的详细叙述
                List<Predicate> predicateList = new ArrayList<>();
                if (searchMap.get("labelname") != null && (!"".equals(searchMap.get("labelname")))) {
                    predicateList.add(cb.like( root.get("labelname").as(String.class), "%" + (String) searchMap.get("labelname") + "%"));
                }
                if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                    predicateList.add(cb.equal(root.get("state").as(String.class), (String) searchMap.get("state")));
                }
                if (searchMap.get("recommend") != null && !"".equals(searchMap.get("recommend"))) {
                    predicateList.add(cb.equal( root.get("recommend").as(String.class), (String) searchMap.get("recommend")));
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
}
