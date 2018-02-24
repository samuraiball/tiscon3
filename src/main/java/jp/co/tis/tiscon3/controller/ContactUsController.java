package jp.co.tis.tiscon3.controller;


import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import enkan.component.BeansConverter;
import enkan.component.doma2.DomaProvider;
import enkan.data.HttpResponse;
import enkan.util.HttpResponseUtils;
import jp.co.tis.tiscon3.dao.ContactUsDao;
import jp.co.tis.tiscon3.entity.ContactUs;
import jp.co.tis.tiscon3.form.ContactUsForm;
import kotowari.component.TemplateEngine;
import static kotowari.routing.UrlRewriter.redirect;
import static enkan.util.HttpResponseUtils.RedirectStatusCode.SEE_OTHER;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * 　お問い合わせページのController
 * 　@author hirooka
 */
public class ContactUsController {
    @Inject
    TemplateEngine templateEngine;

    @Inject
    DomaProvider domaProvider;

    @Inject
    BeansConverter bean;

    private ContactUsDao contactUsDao;

    @PostConstruct
    public void init(){contactUsDao = domaProvider.getDao(ContactUsDao.class);}


    public HttpResponse contactUs(){

        return templateEngine.render("contactUs/contactUsPage","form", new ContactUsForm());
    }

    public HttpResponse modify(ContactUsForm form){
        return templateEngine.render("contactUs/contactUsPage","form",form);
    }

    public HttpResponse confirm(ContactUsForm form){
        if (form.hasErrors()) {
            return templateEngine.render("contactUs/contactUsPage","form",form);
        }
        return templateEngine.render("contactUs/confirm","form",form);
    }

    @Transactional
    public HttpResponse create(ContactUsForm form){
        if(form.hasErrors()){
            return templateEngine.render("contactUs/contactUsPage","form",form);
        }

        ContactUs contactUs = bean.createFrom(form,ContactUs.class);

        contactUsDao.insert(contactUs);
        return redirect(getClass(),"completed", SEE_OTHER);
    }

    public HttpResponse completed(ContactUsForm contactUsForm) {
        return templateEngine.render("contactUs/completed");
    }
}
