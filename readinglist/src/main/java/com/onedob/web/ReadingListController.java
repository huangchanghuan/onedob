package com.onedob.web;

import com.onedob.dao.ReadingListRepository;
import com.onedob.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Author 黄昌焕
 * @Date 2017-06-29  21:18
 */
@Controller
@RequestMapping("/")
public class ReadingListController {
    private final static Logger logger = LoggerFactory.getLogger(ReadingListController.class);

    private ReadingListRepository readingListRepository;

    @Autowired
    public ReadingListController(ReadingListRepository readingListRepository){
        this.readingListRepository = readingListRepository;
    }


    @RequestMapping(value = "/{reader}",method= RequestMethod.GET)
    public String readersBooks(@PathVariable("reader") String reader, Model model){
        List<Book> readingList = readingListRepository.findByReader(reader);
        System.out.println(reader);
        if(readingList!=null){
            model.addAttribute("books", readingList);
        }
        return "readingList";
    }


    @RequestMapping(value = "/{seckillId}/111",method= RequestMethod.GET)
    public String readersBooks11(@PathVariable("seckillId") String reader, Model model){
        List<Book> readingList = readingListRepository.findByReader(reader);

        if(readingList!=null){
            model.addAttribute("books", readingList);
        }
        return "readingList";
    }
}
