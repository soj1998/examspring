package com.rm;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.rm.dao.BookDao;
import com.rm.entity.Book;
import com.rm.util.SimCalculator;
@RequestMapping(value="/book")
@RestController
public class BookController {
	private static final Logger LOG = LoggerFactory.getLogger(BookController.class);
    @Resource
    private BookDao bookDao;
     
    /**
     * 查询所有图书
     * @return
     */
    @RequestMapping(value="/list")
    public ModelAndView list(){
        ModelAndView mav=new ModelAndView();
        //mav.addObject("bookList", bookDao.findAll());
        mav.setViewName("bb.html");
        return mav;
    }
     
 
     
    /**
     * 添加图书
     * @param book
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    public String add(Book book){
    	LOG.info(book.getBookName());
    	SimCalculator sc=new SimCalculator();
    	List<Book> bkall=bookDao.findAll();
    	for(Book b:bkall) {
    		double bl=sc.calculate(b.getBookName(), book.getBookName(), 20);    
    		if(bl>0.8) {
    			LOG.info("存在相同的书籍了");
    			return "already have same book";
    		}
    	}
        bookDao.save(book);
        return "add";
    }
     
    @GetMapping(value="/preUpdate/{id}")
    public ModelAndView preUpdate(@PathVariable("id") Integer id){
        ModelAndView mav=new ModelAndView();
        mav.addObject("book", bookDao.getOne(id));
        mav.setViewName("bookUpdate");
        return mav;
    }
     
    /**
     * 修改图书
     * @param book
     * @return
     */
    @PostMapping(value="/update")
    public String update(Book book){
        bookDao.save(book);
        return "forward:/book/list";
    }
     
    /**
     * 删除图书
     * @param id
     * @return
     */
    @RequestMapping(value="/delete",method=RequestMethod.GET)
    public String delete(int id){
        bookDao.deleteById(id);
        return "forward:/book/list";
    }
}