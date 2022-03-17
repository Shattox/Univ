#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>

#include <iostream>

int main(int argc, char const *argv[]) {
    cv::Mat image = cv::imread(argv[1]);

    cv::Mat outputImg;

    cv::morphologyEx(image, outputImg, 2, cv::getStructuringElement(0, cv::Size(50, 10)));


    cv::imshow("intput", image);
    cv::imshow("output", outputImg);

    cv::waitKey();

    return 0;
}
