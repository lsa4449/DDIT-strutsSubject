package kr.or.ddit.utiles;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

// FileUploadRequestWrapper extends HttpServletRequestWrapper
// HttpServletRequestWrapper extends HttpServletRequest
public class FileUploadRequestWrapper extends HttpServletRequestWrapper {
	private boolean multipartFlag = false;
	// 폼필드
	private Map<String, String[]> parameterMap;
	// 파일
	private Map<String, FileItem[]> fileitemMap;

	public FileUploadRequestWrapper(HttpServletRequest request) {
		this(request, -1, -1);

	}

	public FileUploadRequestWrapper(HttpServletRequest request, int threshold,
			int sizeMax) {
		super(request);

		parsing(request, threshold, sizeMax);
	}

	private void parsing(HttpServletRequest request, int threshold, int sizeMax) {
		this.multipartFlag = ServletFileUpload.isMultipartContent(request);

		// multipart/form-data이면 factory객체 생성
		if (this.multipartFlag) {
			this.parameterMap = new HashMap<String, String[]>();
			this.fileitemMap = new HashMap<String, FileItem[]>();

			// 임시 저장소
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 임시저장소 대상 저장되는 파일의 사이즈 설정 : -1(무제한)
			factory.setSizeThreshold(threshold);

			// 업로드 요청
			ServletFileUpload servletFileUplaod = new ServletFileUpload(factory);
			// 실제저장소 대상 저장되는 파일의 사이즈 설정 : -1(무제한)
			servletFileUplaod.setSizeMax(sizeMax);

			// 파일 입력 태그와 폼필드(일반)태그를 추출하여 Map에 저장한다
			try {
				List<FileItem> items = servletFileUplaod.parseRequest(request);
				for (FileItem item : items) {
					if (item.getSize() > 0) {
						// mem_id, mem_pass, mem_name, files
						String fieldName = item.getFieldName();
						if (item.isFormField()) {
							// 폼 필드
							String value = item.getString("utf-8");

							// ?mem_id=a001&mem_id=b001...
							String[] values = this.parameterMap.get(fieldName);

							if (values == null) {
								values = new String[] { value };
							} else {
								String[] tmp = new String[values.length + 1];
								
								// 배열 복사
								//	스왈로우 카피 : 원본 배열의 각각의 인덱스에서 레퍼런스하는 개별 요소값의 주소가 
								//			     복사 대상 배열과 동일
								//	딥 카피 : 원본 배열의 각각의 인덱스가 레퍼런스 하는 요소값의 주소와 복사 대상
								//		       배열의 인덱스가 레퍼런스하는 요소값의 주소가 상이(신규 생성)
								// 1. 원본 배열
								// 2. 원본 배열의 복사 시작 인덱스
								// 3. 복사 대상 배열
								// 4. 복사 대상 배열의 복사 시작 인덱스
								// 5. 원본 배열에서 복사가 될 전체 인덱스 사이즈
								System.arraycopy(values, 0, tmp, 0, values.length);
								tmp[tmp.length -1] = value;
								values = tmp;
							}
							this.parameterMap.put(fieldName, values);
						} else {
							// 파일
							// ?files=a.png&files=b.gif
							FileItem[] values = this.fileitemMap.get(fieldName);
							if (values == null) {
								values = new FileItem[] { item };
							} else {
								FileItem[] tmp = new FileItem[values.length + 1];
								System.arraycopy(values, 0, tmp, 0,
										values.length);

								tmp[tmp.length - 1] = item;
								values = tmp;
							}
							this.fileitemMap.put(fieldName, values);
						}
					}
				}
			} catch (FileUploadException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	// MultipartRequest에 대표 메소드들
	// 클라이언트로부터 주어진(전달된) 파라미터 값을 리턴한다(String으로 주어진 이름에 대한 값 반환)
	@Override
	public String getParameter(String name) {
		if (this.multipartFlag) {
			// 스트리밍 요청
			String[] values = this.parameterMap.get(name);
			if (values == null) {
				return null;
			} else {
				return values[0];
			}
		} else {
			// 일반 요청
			// super == HttpServletRequest
			return super.getParameter(name);
		}
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		if (this.multipartFlag) {
			// 스트리밍 요청
			return this.parameterMap;
		} else {
			// 일반 요청
			return super.getParameterMap();
		}
	}

	// Enumeration -> 클라이언트로부터 전달된 모든 파라미터의 이름을 리턴한다
	@Override
	public Enumeration<String> getParameterNames() {
		if (this.multipartFlag) {
			return new Enumeration<String>() {
				Iterator<String> keys = parameterMap.keySet().iterator();
				
				@Override
				public boolean hasMoreElements() {
					return keys.hasNext();
				}

				@Override
				public String nextElement() {
					return keys.next();
				}
			};
		} else {
			return super.getParameterNames();
		}
	}

	// 파라미터의 모든 값을 리턴한다
	@Override
	public String[] getParameterValues(String name) {
		if (this.multipartFlag) {
			return this.parameterMap.get(name);
		} else {
			return super.getParameterValues(name);
		}
	}
	
	// 지정한 파라미터 이름과 관련된 FileItem을 리턴한다
	public FileItem getFileItem(String name) {
		if (this.multipartFlag) {
			FileItem[] items = this.fileitemMap.get(name);
			if(items == null) {
				return null;
			} else {
				return items[0];
			}
		} else {
			return null;
		}
	}
	
	public FileItem[] getFileItemValues(String name) {
		if (this.multipartFlag) {
			return this.fileitemMap.get(name);
		} else {
			return null;
		}
	}
	
	public Map<String, FileItem[]> getFileItemMap() {
		if (this.multipartFlag) {
			return this.fileitemMap;
		} else {
			return null;
		}
	}
}
