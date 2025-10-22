package jp.co.sss.lms.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import jakarta.servlet.http.HttpServletRequest;
import jp.co.sss.lms.dto.AttendanceManagementDto;
import jp.co.sss.lms.dto.LoginUserDto;
import jp.co.sss.lms.form.AttendanceForm;
import jp.co.sss.lms.service.StudentAttendanceService;
import jp.co.sss.lms.util.Constants;

/**
 * 勤怠管理コントローラ
 * 
 * @author 東京ITスクール
 */
@Controller
@RequestMapping("/attendance")
public class AttendanceController {

	@Autowired
	private StudentAttendanceService studentAttendanceService;
	@Autowired
	private LoginUserDto loginUserDto;
	

	/**
	 * 勤怠管理画面 初期表示
	 * 
	 * @param lmsUserId
	 * @param courseId
	 * @param model
	 * @return 勤怠管理画面
	 * @throws ParseException
	 */
	@RequestMapping(path = "/detail", method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {

	    // Flashスコープ（前画面からのメッセージ）を取得
	    Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
	    if (flashMap != null) {
	        Object updateMessage = flashMap.get("message");
	        if (updateMessage != null) {
	            model.addAttribute("message", updateMessage.toString());
	        }
	    }

	    //  ログイン情報の取得
	    Integer courseId = loginUserDto.getCourseId();
	    Integer lmsUserId = loginUserDto.getLmsUserId();

	    // 勤怠一覧の取得
	    List<AttendanceManagementDto> attendanceManagementDtoList =
	            studentAttendanceService.getAttendanceManagement(courseId, lmsUserId);
	    model.addAttribute("attendanceManagementDtoList", attendanceManagementDtoList);

	    //  未入力チェック（更新完了メッセージがない時）
	    boolean hasMissingAttendance = checkUnfilledAttendance(lmsUserId);
	    if (hasMissingAttendance && (model.getAttribute("message") == null)) {
	        model.addAttribute("message", "過去日の勤怠に未入力があります。");
	    }

	    return "attendance/detail";
	}



	/**
	 * 勤怠管理画面 『出勤』ボタン押下
	 * 
	 * @param model
	 * @return 勤怠管理画面
	 */
	@RequestMapping(path = "/detail", params = "punchIn", method = RequestMethod.POST)
	public String punchIn(Model model) {

		// 更新前のチェック
		String error = studentAttendanceService.punchCheck(Constants.CODE_VAL_ATWORK);
		model.addAttribute("error", error);
		// 勤怠登録
		if (error == null) {
			String message = studentAttendanceService.setPunchIn();
			model.addAttribute("message", message);
		}
		// 一覧の再取得
		List<AttendanceManagementDto> attendanceManagementDtoList = studentAttendanceService
				.getAttendanceManagement(loginUserDto.getCourseId(), loginUserDto.getLmsUserId());
		model.addAttribute("attendanceManagementDtoList", attendanceManagementDtoList);

		return "attendance/detail";
	}

	/**
	 * 勤怠管理画面 『退勤』ボタン押下
	 * 
	 * @param model
	 * @return 勤怠管理画面
	 */
	@RequestMapping(path = "/detail", params = "punchOut", method = RequestMethod.POST)
	public String punchOut(Model model) {

		// 更新前のチェック
		String error = studentAttendanceService.punchCheck(Constants.CODE_VAL_LEAVING);
		model.addAttribute("error", error);
		// 勤怠登録
		if (error == null) {
			String message = studentAttendanceService.setPunchOut();
			model.addAttribute("message", message);
		}
		// 一覧の再取得
		List<AttendanceManagementDto> attendanceManagementDtoList = studentAttendanceService
				.getAttendanceManagement(loginUserDto.getCourseId(), loginUserDto.getLmsUserId());
		model.addAttribute("attendanceManagementDtoList", attendanceManagementDtoList);

		return "attendance/detail";
	}

	/**
	 * 勤怠管理画面 『勤怠情報を直接編集する』リンク押下
	 * 
	 * @param model
	 * @return 勤怠情報直接変更画面
	 */
	@RequestMapping(path = "/update")
	public String update(Model model) {

		// 勤怠管理リストの取得
		List<AttendanceManagementDto> attendanceManagementDtoList = studentAttendanceService
				.getAttendanceManagement(loginUserDto.getCourseId(), loginUserDto.getLmsUserId());
		// 勤怠フォームの生成
		AttendanceForm attendanceForm = studentAttendanceService
				.setAttendanceForm(attendanceManagementDtoList);
		model.addAttribute("attendanceForm", attendanceForm);

		return "attendance/update";
	}

	/**
	 * 勤怠情報直接変更画面 『更新』ボタン押下
	 * 
	 * @param attendanceForm
	 * @param model
	 * @param result
	 * @return 勤怠管理画面
	 * @throws ParseException
	 */
	/*@RequestMapping(path = "/update", params = "complete", method = RequestMethod.POST)
	public String complete(AttendanceForm attendanceForm, Model model, BindingResult result)
			throws ParseException {

		// 更新
		String message = studentAttendanceService.update(attendanceForm);
		model.addAttribute("message", message);
		// 一覧の再取得
		List<AttendanceManagementDto> attendanceManagementDtoList = studentAttendanceService
				.getAttendanceManagement(loginUserDto.getCourseId(), loginUserDto.getLmsUserId());
		model.addAttribute("attendanceManagementDtoList", attendanceManagementDtoList);

		return "attendance/detail";
	
	}*/
	
	@RequestMapping(path = "/update", params = "complete", method = RequestMethod.POST)
	public String complete(AttendanceForm attendanceForm, RedirectAttributes redirectAttributes)
	        throws ParseException {

		 // 更新
	    String updateMessage = studentAttendanceService.update(attendanceForm);

	    // 登録完了メッセージをFlashに設定
	    redirectAttributes.addFlashAttribute("updateMessage", updateMessage);

	    // 勤怠管理画面へリダイレクト
	    return "redirect:/attendance/detail";
	}

	
/*@RequestMapping(path = "/update", params = "complete", method = RequestMethod.POST)
	public String complete(AttendanceForm attendanceForm, Model model, BindingResult result)
	        throws ParseException {

	    boolean hasError = false;
	    List<String> errorMessages = new ArrayList<>();

	    Pattern timePattern = Pattern.compile("^([01]\\d|2[0-3]):[0-5]\\d$");

	    for (DailyAttendanceForm daily : attendanceForm.getAttendanceList()) {
	        String start = daily.getTrainingStartTime();
	        String end = daily.getTrainingEndTime();

	       
	        if (end != null && !end.isEmpty() && (start == null || start.isEmpty())) {
	            errorMessages.add("出勤時間を入力してください。");
	            hasError = true;
	        }

	        if (start != null && !start.isEmpty() && !timePattern.matcher(start).matches()) {
	            errorMessages.add("出勤時間を正しく入力してください。");
	            hasError = true;
	        }
	        if (end != null && !end.isEmpty() && !timePattern.matcher(end).matches()) {
	            errorMessages.add("退勤時間を正しく入力してください。");
	            hasError = true;
	        }
	    }

	    if (hasError) {
	        model.addAttribute("errorMessages", errorMessages);
	        model.addAttribute("attendanceForm", attendanceForm);
	        return "attendance/update";
	    }

	    
	    String message = studentAttendanceService.update(attendanceForm);
	    model.addAttribute("message", message);

	    // 一覧の再取得
	    List<AttendanceManagementDto> attendanceManagementDtoList = studentAttendanceService
	            .getAttendanceManagement(loginUserDto.getCourseId(), loginUserDto.getLmsUserId());
	    model.addAttribute("attendanceManagementDtoList", attendanceManagementDtoList);

	    return "attendance/detail";
	}*/
	
	/**
	 * 過去日の勤怠に未入力があるか確認する
	 */
	private boolean checkUnfilledAttendance(Integer lmsUserId) {
	    try {
	        Date currentDate = new Date();
	        int missingCount = studentAttendanceService.getUnfilledCount(lmsUserId, currentDate);
	        return missingCount > 0;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

}