<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

			<!-- footer -->
			<footer>
				<div class="footer_wrap">
					<div class="inner fl_wrap fl_left_wrap">
						<!-- Logo -->
						<a href="index.html" class="logo text_blind">MICHUHOL ART MARKET 로고</a>
						<!-- // Logo -->

						<!-- Copy -->
						<div class="">
							<ul class="foot_menu fl_wrap fl_left_wrap">
								<li><a href="other/privacy.html" class="white">개인정보취급방침</a></li>
								<li><a href="other/terms.html">이용약관</a></li>
							</ul>
							<p>
								<span>우)22169 인천광역시 미추홀구 독정이로 95 TEL:032-880-4763 </span>
								<span>Copyright(c) 2010 Incheon Michuhol-Gu. All Rights Reserved.</span>
							</p>
						</div>
						<!-- // Copy -->
					</div>
				</div>
			</footer>
			<!-- // footer -->
		</div>

		<script>
			$(function() {
				$(".event_slide").bxSlider({
					pager: false
				});

				$(".banner_slide").bxSlider({
					mode: "fade",
					controls: false
				});

				$(".notice_slide").bxSlider({
					mode: "vertical",
					pager: false
				});
			});

			/**
			 * 로그아웃을 처리하는 form을 생성하여 submit 실행
			 */
			function logout() {
				var html = "";
				html += '<form name="logoutForm" action="/logout.do" method="post">';
					html += '<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />';
				html += '</form>';

				$("body").append(html);
				document.logoutForm.submit();
			}
		</script>
	</body>
</html>
