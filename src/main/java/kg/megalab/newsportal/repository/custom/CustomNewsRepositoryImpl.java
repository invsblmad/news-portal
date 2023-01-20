package kg.megalab.newsportal.repository.custom;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import kg.megalab.newsportal.dto.response.data.DetailedNewsDto;
import kg.megalab.newsportal.dto.response.data.NewsDto;
import kg.megalab.newsportal.dto.response.data.UserNewsDto;
import kg.megalab.newsportal.exceptions.NewsNotFoundException;
import kg.megalab.newsportal.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomNewsRepositoryImpl implements CustomNewsRepository {

    private final EntityManager em;

    private QNews qNews;
    private QFavouriteNews qFavouriteNews;

    @PostConstruct
    private void init() {
        qNews = QNews.news;
        qFavouriteNews = QFavouriteNews.favouriteNews;
    }

    private Expression<?>[] getArrayOfExpressions(Integer userId, List<Expression<?>> expressions) {
        if (userId != null) expressions.add(qFavouriteNews.news.id.isNotNull());

        Expression<?>[] array = new Expression[expressions.size()];
        return expressions.toArray(array);
    }

    private <T> JPQLQuery<T> getQuery(Integer userId, Class<T> type, List<Expression<?>> expressions) {
        JPQLQuery<T> query = new JPAQuery<>(em).select(Projections.constructor(
                        type, getArrayOfExpressions(userId, expressions))).from(qNews);

        if (userId != null) {
            return query.leftJoin(qFavouriteNews).on(
                    qNews.id.eq(qFavouriteNews.news.id).and(qFavouriteNews.user.id.eq(userId)));
        } else {
            return query;
        }
    }

    private <T, Q> Page<T> getPaginatedResult(JPQLQuery<T> query, Pageable pageable, Class<Q> type) {
        Querydsl querydsl = new Querydsl(em, (new PathBuilderFactory()).create(type));
        List<T> result = querydsl.applyPagination(pageable, query).fetch();

        return new PageImpl<>(result, pageable, query.fetchCount());
    }

    @Override
    public Page<NewsDto> findAll(List<Integer> categories, String search, Integer userId, Pageable pageable) {
        List<Expression<?>> expressions = new ArrayList<>(
                List.of(qNews.id, qNews.title, qNews.shortDescription, qNews.image, qNews.date)
        );

        JPQLQuery<NewsDto> query = getQuery(userId, NewsDto.class, expressions);
        query.orderBy(qNews.date.desc());

        if (categories != null)
            query.where(qNews.category.id.in(categories));
        if (search != null)
            query.where(qNews.title.lower().like("%" + search.toLowerCase() + "%"));

        return getPaginatedResult(query, pageable, News.class);
    }

    @Override
    public DetailedNewsDto findById(int newsId, Integer userId) {
        List<Expression<?>> expressions = new ArrayList<>(
                List.of(qNews.id, qNews.title, qNews.shortDescription, qNews.text, qNews.image, qNews.date)
        );

        JPQLQuery<DetailedNewsDto> query = getQuery(userId, DetailedNewsDto.class, expressions);
        DetailedNewsDto news = query.where(qNews.id.eq(newsId)).fetchOne();

        if (news == null)
            throw new NewsNotFoundException("The news with such id doesn't exist");
        return news;
    }

    @Override
    public Page<NewsDto> findUserFavouriteNews(String search, int userId, Pageable pageable) {
        JPQLQuery<NewsDto> query = new JPAQuery<>(em)
                .select(Projections.constructor(
                    NewsDto.class, qFavouriteNews.news.id, qFavouriteNews.news.title,
                    qFavouriteNews.news.shortDescription, qFavouriteNews.news.image,
                    qFavouriteNews.news.date, Expressions.TRUE))
                .from(qFavouriteNews)
                .where(qFavouriteNews.user.id.eq(userId))
                .orderBy(qFavouriteNews.news.date.desc());

        if (search != null)
            query.where(qFavouriteNews.news.title.lower().like("%" + search.toLowerCase() + "%"));

        return getPaginatedResult(query, pageable, FavouriteNews.class);
    }

    @Override
    public Page<UserNewsDto> findAllUserNews(int userId, String search, Pageable pageable) {
        JPQLQuery<UserNewsDto> query = new JPAQuery<>(em)
                .select(Projections.constructor(
                    UserNewsDto.class, qNews.id, qNews.title, qNews.shortDescription,
                        qNews.image, qNews.date))
                .from(qNews)
                .where(qNews.user.id.eq(userId));

        if (search != null)
            query.where(qNews.title.lower().like("%" + search.toLowerCase() + "%"));

        return getPaginatedResult(query, pageable, UserNewsDto.class);
    }

}
