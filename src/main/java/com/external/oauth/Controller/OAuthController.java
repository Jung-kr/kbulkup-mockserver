package com.external.oauth.Controller;

import com.external.oauth.dto.TokenRequest;
import com.external.oauth.dto.TokenResponse;
import com.external.oauth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    private final OAuthService oAuthService;

    /**
     * Step 4: OAuth 로그인 페이지 표시
     */
    @GetMapping("/authorize")
    public String showAuthorizePage() {
        return "oauth_login";
    }

    /**
     * Step 6: 사용자 인증 및 Authorization Code 발급
     * @param clientId: 이용기관 식별자 (어떤 앱이 요청하는지)
     * @param redirectUri: 인증 후 돌아갈 주소
     * @param scope: 요청 권한 (login, inquiry, transfer)
     * @param state: CSRF 방지용 랜덤값
     * @param responseType: 응답 타입 (항상 "code")
     * @return
     */
    @PostMapping("/authorize")
    public String authenticate(
            @RequestParam("user_name") String userName,
            @RequestParam("client_id") String clientId,
            @RequestParam("redirect_uri") String redirectUri
    ) {
        String authCode = oAuthService.registerUserAndIssueCode(userName, clientId);
        return "redirect:" + redirectUri + "?authCode=" + authCode;
    }

    /**
     * Step 8: JWT 토큰 발급
     */
    @ResponseBody
    @PostMapping("/token")
    public TokenResponse issueToken(@RequestBody TokenRequest request) {
        return oAuthService.issueToken(request.getAuthCode());
    }
}
