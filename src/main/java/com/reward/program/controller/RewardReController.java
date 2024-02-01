package com.reward.program.controller;

import java.util.List;
import java.util.Optional;

import com.reward.program.model.Reward;
import com.reward.program.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/rewards")
public class RewardReController {

	@Autowired
	private RewardService rewardService;

	@GetMapping
	public ResponseEntity<List<Reward>> getRewards(@RequestParam Optional<Long> customerId) {
		List<Reward> rewards = customerId.isPresent() ? List.of(rewardService.rewardByCustomerId(customerId.get()))
				: rewardService.calculateAllReward();
		return new ResponseEntity<List<Reward>>(rewards, HttpStatus.OK);
	}
}
