package com.nwidart.techvideo.unit.service;

import com.nwidart.techvideo.entity.Video;
import com.nwidart.techvideo.entity.Vote;
import com.nwidart.techvideo.repository.VoteRepository;
import com.nwidart.techvideo.service.VoteService;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class VoteServiceTest {

  @Mock
  private VoteRepository voteRepository;

  private VoteService voteService;

  @Before
  public void setUp() throws Exception {
    this.voteService = new VoteService(voteRepository);
  }

  @Test
  public void itCanSubmitANewVote() {
    Mockito
        .when(voteRepository.save(ArgumentMatchers.any(Vote.class)))
        .thenReturn(new Vote(1, new Video("My video", "youtube.com/myvideo")));

    Vote vote = voteService.submitNewVote(1);

    Mockito.verify(voteRepository).save(ArgumentMatchers.any(Vote.class));
    Assert.assertEquals("My video", vote.getVideo().getTitle());
  }

  @Test
  public void allReturnsAllVotes() {
    Mockito.when(voteRepository.findAll()).thenReturn(listOfVotes());

    List<Vote> votes = voteService.all();

    Mockito.verify(voteRepository).findAll();
    Assert.assertEquals(2, votes.size());
  }

  private List<Vote> listOfVotes() {
    return List.of(
        new Vote(1, new Video("title", "url")),
        new Vote(2, new Video("title 2", "url 2"))
    );
  }
}
