package com.mfc.coordinating.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// 추후 로드밸런싱 활용해서 직접 서비스에 접속 가능하도록 변경 (유레카 서버로 가능 포트 안거쳐도 됨)
@FeignClient(name = "auth-service", url = "https://myfaco.shop/auth-service")
public interface AuthClient {
	@GetMapping("/members/birth-gender/{uuid}")
	AuthInfoResponse getAuthInfo(@PathVariable String uuid);
}
