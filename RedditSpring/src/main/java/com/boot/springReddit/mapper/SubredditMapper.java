package com.boot.springReddit.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.boot.springReddit.dto.SubredditDto;
import com.boot.springReddit.model.Post;
import com.boot.springReddit.model.Subreddit;
import com.boot.springReddit.model.User;


@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    //@Mapping(target = "createdDate",source="createdDate")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "user", source = "user")
    Subreddit mapDtoToSubreddit(SubredditDto subredditDto, User user);
}
