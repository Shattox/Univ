#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/highgui/highgui.hpp>

#include <iostream>

void display_pixel_color(cv::Mat image, int x, int y) {
  for (int i = 2; i >= 0; i--) {
    std::cout << (int) image.at<cv::Vec3b>(50, 100)[i] << " ";
  }
  std::cout << std::endl;
}

void change_pixel_color(cv::Mat image, int x, int y) {
  image.at<cv::Vec3b>(x, y)[0] = 0;
  image.at<cv::Vec3b>(x, y)[1] = 0;
  image.at<cv::Vec3b>(x, y)[2] = 255;
}

void change_line_color(cv::Mat image, int line) {
  for (int j = 0; j < image.cols; ++j) {
    image.at<cv::Vec3b>(line, j)[0] = 0;
    image.at<cv::Vec3b>(line, j)[1] = 0;
    image.at<cv::Vec3b>(line, j)[2] = 255;
  }
}

void add_value_to_each_pixel(cv::Mat image, int value) {
  for (int j = 0; j < image.cols; ++j) {
    for (int i = 0; i < image.rows; ++i) {
      for (int c = 0; c < 3; ++c) {
        image.at<cv::Vec3b>(i, j)[c] += value;
      }
    }
  }
}

int clamp(int x) {
  return (unsigned char) std::min(std::max(x, 0), 255);
}

void add_value_to_each_pixel_clamp(cv::Mat image, int value) {
  for (int j = 0; j < image.cols; ++j) {
    for (int i = 0; i < image.rows; ++i) {
      for (int c = 0; c < 3; ++c) {
        image.at<cv::Vec3b>(i, j)[c] = clamp((int) image.at<cv::Vec3b>(i, j)[0] + value);
      }
    }
  }
}

void negative_image(cv::Mat image) {
  for (int j = 0; j < image.cols; ++j) {
    for (int i = 0; i < image.rows; ++i) {
      for (int c = 0; c < 3; ++c) {
        image.at<cv::Vec3b>(i, j)[c] = 255 - image.at<cv::Vec3b>(i, j)[c];
      }
    }
  }
}

void convert_gray(cv::Mat& image) {
  cv::cvtColor(image, image, cv::COLOR_BGR2GRAY);
}

void q8(cv::Mat image) {
  for (int i = 0; i < image.rows; ++i) {
    for (int j = 0; j < image.cols; ++j) {
      if (image.at<unsigned char>(i, j) > 50) {
        image.at<unsigned char>(i, j) = 255;
      } else {
        image.at<unsigned char>(i, j) = 0;
      }
    }
  }
}

//////////////////////////////////////////////////////////////////////////////////////////////////
int main(int argc, char ** argv) {
  // check arguments
  if(argc != 2) {
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


  // do something
  display_pixel_color(image, 50, 100);
  /* change_pixel_color(image, 10, 20);
  change_line_color(image, 42);
  add_value_to_each_pixel(image, 50);
  add_value_to_each_pixel_clamp(image, 50);*/
  /* negative_image(image); */
  convert_gray(image);
  /* q8(image); */

  // display an image
  cv::imshow("une image", image);
  std::cout << "appuyer sur une touche ..." << std::endl;
  cv::waitKey();

  // save the image
  cv::imwrite("output/tp1ex1.jpg",image);

  return 1;
}
