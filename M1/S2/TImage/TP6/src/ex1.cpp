#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>

#include <iostream>

int main(int argc, char const *argv[]) {
    cv::Mat image = cv::imread(argv[1]);

    cv::Mat outputImg;

    cv::imshow("intput", image);

    cv::morphologyEx(image, outputImg, 3, cv::getStructuringElement(0, cv::Size(5, 5)));
    cv::dilate(outputImg, image, cv::getStructuringElement(0, cv::Size(10, 10)));
    //cv::erode(outputImg, image, cv::getStructuringElement(0, cv::Size(10, 10)));
    cv::morphologyEx(image, outputImg, 2, cv::getStructuringElement(0, cv::Size(5, 5)));
    //cv::erode(outputImg, image, cv::getStructuringElement(0, cv::Size(10, 10)));
    cv::erode(outputImg, image, cv::getStructuringElement(0, cv::Size(10, 10)));

    cv::imshow("output", image);

    cv::waitKey();

    return 0;
}
