package ru.s3v3nny.urlshortener.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.s3v3nny.urlshortener.models.LinkModel;
import ru.s3v3nny.urlshortener.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class LinkDAO {

    public LinkModel getByKey (String key) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(LinkModel.class, key);
    }

    public void update(LinkModel link) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(link);
        tx.commit();
        session.close();
    }

    public void delete(LinkModel link) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(link);
        tx.commit();
        session.close();
    }

    public List<LinkModel> getAllLinks () {
        return null;
    }
}
