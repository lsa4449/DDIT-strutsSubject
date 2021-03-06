package kr.or.ddit.fquestion.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;

import kr.or.ddit.vo.FilesVO;
import kr.or.ddit.vo.PostVO;

public interface IFQuestionBoardDao {
	/**
	 * FQuestionBoard 검색, 전체 들로 가져온다.
	 * @param Map<String, String> params
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> selectFQuestionBoard(Map<String, String> params) throws Exception;
	
	/**
	 * FQuestionBoardInfo 를 가져온다. 공지사항 1개에 대한 값을 가져온다.
	 * @param Map<String, String> params
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> FQuestionBoardInfo(Map<String, String> params) throws Exception;
	
	/**
	 * 파일까지 인서트 할수 있게 한다.
	 * @param postVOInfo
	 * @param fileItemValues
	 * @return String
	 */
	public String insertFQuestionboard(PostVO postVOInfo) throws Exception;
	
	/**
	 * 업데이트 한다. 인자 값은 postVO에 있는 값들 중에서 title, content 이고 조건은 post_no 이다.
	 * @param postVO 
	 */
	public void updateFQuestionBoard(PostVO postVO) throws Exception;

	/**
	 * 삭제 한다. 공지사항 게시판의 번호를 가지고 삭제한다.
	 * @param params
	 */
	public void deleteFQuestionBoardInfo(Map<String, String> params) throws Exception;
	
	/**
	 * 검색 조건에 맞는 list의 개수 반환
	 * @param params
	 * @return String 타입이기는 하나 list의 갯수를 String 타입으로 받은 것일뿐
	 */
	public String totalCount(Map<String, String> params) throws Exception;
	
	/**
	 * FQ에서 file 경로 가져오기
	 * @param params List<Map<String, String>>
	 * @return
	 */
	public List<Map<String, String>> selectFQuestionImageName(
			Map<String, String> params) throws Exception;
	
	
	/**
	 * 파일 제외 하고 insert 만 되도록
	 * @param postVOInfo
	 */
	public void insertboard(PostVO postVOInfo) throws Exception;

	/**
	 * 조회수 증가
	 * @param params
	 */
	public void udpateHit(Map<String, String> params) throws Exception;

	
	public String insertPostReply(PostVO postInfo) throws Exception;
	
}
