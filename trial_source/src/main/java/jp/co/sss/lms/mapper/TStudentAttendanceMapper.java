package jp.co.sss.lms.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jp.co.sss.lms.dto.AttendanceManagementDto;
import jp.co.sss.lms.entity.TStudentAttendance;

/**
 * 勤怠情報（受講生入力）テーブルマッパー
 * 
 * @author 東京ITスクール
 */
@Mapper
public interface TStudentAttendanceMapper {

	/**
	 * 勤怠情報（受講生入力）取得（LMSユーザーID）
	 * 
	 * @param lmsUserId
	 * @param deleteFlg
	 * @return 勤怠情報（受講生入力）エンティティ
	 */
	List<TStudentAttendance> findByLmsUserId(
			@Param("lmsUserId") Integer lmsUserId,
			@Param("deleteFlg") Short deleteFlg);

	/**
	 * 勤怠情報（受講生入力）取得（LMSユーザーID＆日付）
	 * 
	 * @param lmsUserId
	 * @param trainingDate
	 * @param deleteFlg
	 * @return 勤怠情報（受講生入力）エンティティ
	 */
	TStudentAttendance findByLmsUserIdAndTrainingDate(@Param("lmsUserId") Integer lmsUserId,
			@Param("trainingDate") Date trainingDate, @Param("deleteFlg") Short deleteFlg);

	/**
     * 勤怠情報登録
     */
    Boolean insert(TStudentAttendance tStudentAttendance);

    /**
     * 勤怠情報更新
     */
    Boolean update(TStudentAttendance tStudentAttendance);
	
	 /**
     * 勤怠管理画面用DTOリスト取得
     * @author 吉村健 - Task.25
     */
    List<AttendanceManagementDto> getAttendanceManagement(
            @Param("courseId") Integer courseId,
            @Param("lmsUserId") Integer lmsUserId,
            @Param("deleteFlg") Short deleteFlg);

    /**
     * 未入力件数を取得
     * @param lmsUserId  ユーザーID
     * @param deleteFlg  削除フラグ
     * @param trainingDate  日付
     * @return 未入力件数
     */
    int getUnfilledCount(
            @Param("lmsUserId") Integer lmsUserId,
            @Param("deleteFlg") Short deleteFlg,
            @Param("trainingDate") Date trainingDate
    );
	int getUnfilledCount(Map<String, Object> params);
}
