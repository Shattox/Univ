#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/highgui/highgui.hpp>

#include <iostream>


//////////////////////////////////////////////////////////////////////////////////////////////////
void callBackKeyboard(int event, int x, int y, int flags, void* userdata)
{
   switch(event){
    case cv::EVENT_LBUTTONDOWN : 
      std::cout << "left button pressed at : " << x << ", " << y << std::endl;
      break;

    case cv::EVENT_RBUTTONDOWN : 
    case cv::EVENT_MBUTTONDOWN : 
    case cv::EVENT_MOUSEMOVE   : 
      break;
   }
}

void extrapolation(const cv::Mat& image) {
  int size = 3;
  cv::Mat newImage = cv::Mat(3 * image.rows, 3 * image.cols, image.type());

  for (int k = 0; k < size; k++) {
    for (int l = 0; l < size; l++) {
      for (int j = 0; j < image.cols; ++j) {
        for (int i = 0; i < image.rows; ++i) {
          for (int c = 0; c < 3; c++) {
            if ((k == 0 && l == 0) || (k == size - 1 && l == size - 1) || (k == size - 1 && l == 0) || (k == 0 && l == size - 1)) {
              newImage.at<cv::Vec3b>(i + k * image.rows, j + l * image.cols)[c] = image.at<cv::Vec3b>(image.rows + 1 - i, image.cols + 1 - j)[c];
            }/*  else if () {
              newImage.at<cv::Vec3b>(i + k * image.rows, j + l * image.cols)[c] = image.at<cv::Vec3b>(image.rows + 1 - i, j)[c];
            } */
            else {
              newImage.at<cv::Vec3b>(i + k * image.rows, j + l * image.cols)[c] = image.at<cv::Vec3b>(i, j)[c];
            }
          }
        }
      }
    }
  }
  
  cv::imshow("newImage", newImage);  
}

//////////////////////////////////////////////////////////////////////////////////////////////////
int main(int argc, char ** argv)
{
  // check arguments
  if(argc != 2){
    std::cout << "usage: " << argv[0] << " image" << std::endl;
    return -1;
  }

  // load the input image
  std::cout << "load image ..." << std::endl;
  cv::Mat image = cv::imread(argv[1]);
  if(image.empty()){
    std::cout << "error loading " << argv[1] << std::endl;
    return -1;
  }
  std::cout << "image size : " << image.cols << " x " << image.rows << std::endl;

  // setup a window
  cv::namedWindow("image", 1);
  cv::setMouseCallback("image", callBackKeyboard, NULL);


  bool loopOn = true;

  while(loopOn){

    cv::imshow("image", image);
    extrapolation(image);

    int key = cv::waitKey(500) % 256;
    if(key == 27 || key == 'q')
      loopOn = false;   
  }


  return 1;
}
