#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>

#include <iostream>

#include "fourierTransform.hpp"

cv::Mat sobelXFourierKernel(const int width, const int height) {
  cv::Mat image = cv::Mat(cv::Size(width, height), CV_32F, cv::Scalar(0.0));

  image.at<float>(image.rows / 2 - 1, image.cols / 2 - 1) = -1;
  image.at<float>(image.rows / 2, image.cols / 2 - 1) = -2;
  image.at<float>(image.rows / 2 - 1, image.cols / 2 + 1) = -1;
  image.at<float>(image.rows / 2 - 1, image.cols / 2) = 0;
  image.at<float>(image.rows / 2, image.cols / 2) = 0;
  image.at<float>(image.rows / 2, image.cols / 2 - 1) = 0;
  image.at<float>(image.rows / 2 - 1, image.cols / 2 + 1) = 1;
  image.at<float>(image.rows / 2, image.cols / 2 + 1) = 2;
  image.at<float>(image.rows / 2 + 1, image.cols / 2 + 1) = 1;

  return image;
}

cv::Mat sobelYFourierKernel(const int width, const int height) {
  cv::Mat image = cv::Mat(cv::Size(width, height), CV_32F, cv::Scalar(0.0));

  image.at<float>(image.rows / 2 - 1, image.cols / 2 - 1) = -1;
  image.at<float>(image.rows / 2, image.cols / 2 - 1) = 0;
  image.at<float>(image.rows / 2 - 1, image.cols / 2 + 1) = 1;
  image.at<float>(image.rows / 2 - 1, image.cols / 2) = -2;
  image.at<float>(image.rows / 2, image.cols / 2) = 0;
  image.at<float>(image.rows / 2, image.cols / 2 - 1) = 2;
  image.at<float>(image.rows / 2 - 1, image.cols / 2 + 1) = -1;
  image.at<float>(image.rows / 2, image.cols / 2 + 1) = 0;
  image.at<float>(image.rows / 2 + 1, image.cols / 2 + 1) = 1;

  return image;
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

  // convert to gray scale
  cv::cvtColor(image, image, cv::COLOR_RGB2GRAY);




  // discrete Fourier Transform
  cv::Mat imageFourierMagnitude, imageFourierPhase;
  cv::Mat filtre = sobelYFourierKernel(image.cols, image.rows);

  discreteFourierTransform(filtre, imageFourierMagnitude, imageFourierPhase);

  // do someting here ....
  /* imageFourierMagnitude.at<float>(imageFourierMagnitude.rows / 2, imageFourierMagnitude.cols / 2 - 10) = 0;
  imageFourierMagnitude.at<float>(imageFourierMagnitude.rows / 2, imageFourierMagnitude.cols / 2 + 10) = 0; */

  cv::Mat newImage = cv::Mat(cv::Size(image.cols, image.rows), CV_32F, cv::Scalar(0.0));

  for (int i = 0; i < image.rows; i++) {
    for (int j = 0; j < image.cols; j++) {
      newImage.at<float>(i, j) = imageFourierMagnitude.at<float>(i, j) * imageFourierPhase.at<float>(i, j);
    }
  }

  // filtre passe bas
  // removeRing(imageFourierMagnitude, 40, 1000);

  //filtre passe haut
  // removeRing(imageFourierMagnitude, 0.5, 10);

  //filtre passe bande
  // removeRing(imageFourierMagnitude, 20, 1000);
  // removeRing(imageFourierMagnitude, 0, 10);

  // inverse Fourier Transform
  cv::Mat outputImage;
  inverseDiscreteFourierTransform(newImage, imageFourierPhase, outputImage, CV_8U);


  // display everything
  cv::namedWindow("Input image");
  cv::namedWindow("Magnitude");
  cv::namedWindow("Output image");

  cv::moveWindow("Input image",100, 50);
  cv::moveWindow("Magnitude",700, 50);
  cv::moveWindow("Output image",100, 400);

  cv::imshow("Input image", image);
  cv::imshow("Magnitude", fourierMagnitudeToDisplay(imageFourierMagnitude));
  cv::imshow("Output image", outputImage);
  cv::waitKey();
 
  // save the images
  cv::imwrite("output/inputImage.jpg",image);
  cv::imwrite("output/magnitude.png", fourierMagnitudeToDisplay(imageFourierMagnitude));
  cv::imwrite("output/pahse.png", fourierPhaseToDisplay(imageFourierPhase));
  cv::imwrite("output/filteredImage.png",outputImage);

  return 0;
}
